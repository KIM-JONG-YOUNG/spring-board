package edu.jong.board.member.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.jong.board.client.exception.ResourceDeactiveException;
import edu.jong.board.client.exception.ResourceExistsException;
import edu.jong.board.client.exception.ResourceNotFoundException;
import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.BoardConstants.CacheKeys;
import edu.jong.board.common.type.MemberSortBy;
import edu.jong.board.common.type.OrderBy;
import edu.jong.board.common.type.State;
import edu.jong.board.domain.utils.QueryDslUtils;
import edu.jong.board.member.entity.MemberEntity;
import edu.jong.board.member.entity.QMemberEntity;
import edu.jong.board.member.exception.PasswordNotMatchedException;
import edu.jong.board.member.mapper.MemberMapper;
import edu.jong.board.member.repository.MemberRepository;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberPasswordModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import edu.jong.board.redis.service.RedisService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final PasswordEncoder encoder;
	private final MemberMapper memberMapper;
	private final MemberRepository memberRepository;
	
	private final RedisService redisService;
	
	private final JPAQueryFactory jpaQueryFactory;
	private final QMemberEntity TB_MEMBER = QMemberEntity.memberEntity;
	
	@PostConstruct 
	private void init() {
		redisService.changeObjectMapper(new ObjectMapper()
				.registerModule(new JavaTimeModule()));
	}

	@Transactional
	@Override
	public long addMember(@NotNull @Valid MemberAddParam param) {
		
		// 동일한 계정 존재 여부 확인 
		if (memberRepository.existsByUsername(param.getUsername()))
			throw new ResourceExistsException("동일한 계정이 존재합니다.");
			
		// 사용자 생성 및 저장  
		MemberEntity member = memberRepository.save(memberMapper.toEntity(param, encoder));
		
		// 저장된 사용자 정보 캐시 저장 
		redisService.caching(
				CacheKeys.MEMBER_KEY + member.getNo(), 
				memberMapper.toDetails(member), 60);
		
		return member.getNo();
	}

	@Transactional
	@Override
	public long modifyMember(long memberNo, @NotNull @Valid MemberModifyParam param) {

		// 사용자 정보 조회 - 다른 트랜잭션이 접근 불가 (배타적 잠금)
		MemberEntity member = memberRepository.findByIdForUpdate(memberNo)
				.orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다."));
		
		// 사용자 정보 수정 및 저장  
		memberRepository.save(memberMapper.updateEntity(param, member));
		
		// 수정된 사용자 정보 캐시 저장 
		redisService.caching(
				CacheKeys.MEMBER_KEY + member.getNo(), 
				memberMapper.toDetails(member), 60);

		return member.getNo();
	}

	@Transactional
	@Override
	public long modifyMemberPassword(long memberNo, @NotNull @Valid MemberPasswordModifyParam param) {
		
		// 사용자 정보 조회 - 다른 트랜잭션이 접근 불가 (배타적 잠금)
		MemberEntity member = memberRepository.findByIdForUpdate(memberNo)
				.orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다."));

		// 현재 비밀번호 확인 
		if (!encoder.matches(param.getCurPassword(), member.getPassword()))
			throw new PasswordNotMatchedException("비밀번호가 일치하지 않습니다.");
		
		// 사용자 비밀번호 수정 및 저장  
		memberRepository.save(memberMapper.updateEntity(param, encoder, member));

		return member.getNo();
	}

	@Transactional
	@Override
	public long modifyMemberState(long memberNo, @NotNull State state) {
		
		// 사용자 정보 조회 - 다른 트랜잭션이 접근 불가 (배타적 잠금)
		MemberEntity member = memberRepository.findByIdForUpdate(memberNo)
				.orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다."));

		// 사용자 상태값 변경
		member.setState(state);
		
		// 활성화 여부에 따라 캐시 저장 혹은 삭제
		String cacheKey = CacheKeys.MEMBER_KEY + member.getNo();
		if (state == State.ACTIVE) {
			redisService.caching(cacheKey, 
					memberMapper.toDetails(member), 60);
		} else {
			redisService.remove(cacheKey);
		}
		
		return member.getNo();
	}

	@Transactional(readOnly = true)
	@Override
	public MemberDetails getMember(long memberNo) {
		
		// 캐시키와 타입 정의 
		String cacheKey = CacheKeys.ROLE_KEY + memberNo;
		TypeReference<MemberDetails> type = new TypeReference<MemberDetails>() {};

		// 캐시 데이터 조회 
		return redisService.get(cacheKey, type).orElseGet(() -> {

			// 캐시에 없을 경우 DB 조회 
			MemberDetails details = memberMapper.toDetails(memberRepository.findById(memberNo)
					.orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다.")));

			// 활성화 상태가 아닐 경우 오류 발생 
			if (details.getState() == State.DEACTIVE)
				throw new ResourceDeactiveException("비활성화된 사용자입니다.");
				
			// 조회한 데이터 캐시 저장 
			redisService.caching(cacheKey, details, 60);

			return details;
		});
	}

	private BooleanExpression[] getBooleanExpressions(MemberSearchCond cond) {
		
		LocalDateTime from = (cond.getFrom() == null) ? null : 
			LocalDateTime.of(cond.getFrom(), LocalTime.of(0, 0, 0));
		LocalDateTime to = (cond.getTo() == null) ? null : 
			LocalDateTime.of(cond.getTo(), LocalTime.of(23, 59, 59));
		
		return new BooleanExpression[] {
				QueryDslUtils.containsIfPresent(TB_MEMBER.username, cond.getUsername()),
				QueryDslUtils.containsIfPresent(TB_MEMBER.name, cond.getName()),
				QueryDslUtils.equalsIfPresent(TB_MEMBER.gender, cond.getGender()),
				QueryDslUtils.betweenIfPresent(TB_MEMBER.createdDate, from, to),
				QueryDslUtils.equalsIfPresent(TB_MEMBER.state, State.ACTIVE)
		};
	}
	
	private OrderSpecifier<?> getOrderSpecifier(MemberSearchCond cond) {

		MemberSortBy sortBy = cond.getSortBy();
		OrderBy orderBy = cond.getOrderBy();
		
		ComparableExpressionBase<?> sortCol = null;

		// 정렬에 사용할 컬럼 지정 
		if (sortBy == MemberSortBy.USERNAME) {
			sortCol = TB_MEMBER.username;
		} else if (sortBy == MemberSortBy.NAME) {
			sortCol = TB_MEMBER.name;
		} else if (sortBy == MemberSortBy.GENDER) {
			sortCol = TB_MEMBER.gender;
		} else if (sortBy == MemberSortBy.EMAIL) {
			sortCol = TB_MEMBER.email;
		} else if (sortBy == MemberSortBy.CREATED_DATE) {
			sortCol = TB_MEMBER.createdDate;
		} else if (sortBy == MemberSortBy.UPDATED_DATE) {
			sortCol = TB_MEMBER.updatedDate;
		} else {
			sortCol = TB_MEMBER.no;
		}
		
		// 정렬 방식 지정  
		return (orderBy == OrderBy.DESC) 
				? sortCol.desc()
				: sortCol.asc();
	} 
	
	@Transactional(readOnly = true)
	@Override
	public PagingList<MemberDetails> searchMemberList(@NotNull @Valid MemberSearchCond cond) {

		// 조회 조건 및 정렬 조건 생성 
		BooleanExpression[] queryConditions = getBooleanExpressions(cond);
		OrderSpecifier<?> orderCondition = getOrderSpecifier(cond);

		// 페이징을 위한 Offset 
		long offset = (cond.getPage() - 1) * cond.getPageRows();
		if (offset < 0) offset = 0;

		// 조건에 해당하는 권한의 총 개수 
		long totalCount = jpaQueryFactory
				.select(TB_MEMBER.count())
				.from(TB_MEMBER)
				.where(queryConditions)
				.fetchOne();

		// 조건에 해당하는 권한 목록 조회 - 페이징
		List<MemberDetails> list = jpaQueryFactory
				.selectFrom(TB_MEMBER)
				.where(queryConditions)
				.orderBy(orderCondition)
				.offset(offset).limit(cond.getPageRows())
				.fetch().stream()
				.map(x -> memberMapper.toDetails(x))
				.collect(Collectors.toList());

		return new PagingList<MemberDetails>(list, cond, totalCount);
	}

}
