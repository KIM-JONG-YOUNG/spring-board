package edu.jong.board.role.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import edu.jong.board.common.type.OrderBy;
import edu.jong.board.common.type.RoleSortBy;
import edu.jong.board.common.type.State;
import edu.jong.board.domain.utils.QueryDslUtils;
import edu.jong.board.member.entity.QRoleEntity;
import edu.jong.board.member.entity.RoleEntity;
import edu.jong.board.member.repository.RoleRepository;
import edu.jong.board.redis.service.RedisService;
import edu.jong.board.role.mapper.RoleMapper;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleMapper roleMapper; 
	private final RoleRepository roleRepository;
	
	private final RedisService redisService;
	
	private final JPAQueryFactory jpaQueryFactory;
	private final QRoleEntity TB_ROLE = QRoleEntity.roleEntity;
	
	@PostConstruct 
	private void init() {
		redisService.changeObjectMapper(new ObjectMapper()
				.registerModule(new JavaTimeModule()));
	}
	
	@Transactional
	@Override
	public long addRole(@NotNull @Valid RoleAddParam param) {
		
		// 동일한 권한명 존재 여부 확인 
		if (roleRepository.existsByName(param.getName()))
			throw new ResourceExistsException("동일한 권한명이 존재합니다.");
		
		// 권한 생성 및 저장 
		RoleEntity role = roleRepository.save(roleMapper.toEntity(param));
		
		// 저장된 권한 정보 캐시 저장 
		redisService.caching(
				CacheKeys.ROLE_KEY + role.getNo(), 
				roleMapper.todDetails(role), 60);

		return role.getNo();
	}

	@Transactional
	@Override
	public long modifyRole(long roleNo, @NotNull @Valid RoleModifyParam param) {

		// 권한 정보 조회 - 다른 트랜잭션이 접근 불가 (배타적 잠금)
		RoleEntity role = roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new ResourceNotFoundException("권한이 존재하지 않습니다."));
		
		// 권한 수정 및 저장 
		roleRepository.save(roleMapper.updateEntity(param, role));

		// 수정된 권한 정보 캐시 저장 
		redisService.caching(
				CacheKeys.ROLE_KEY + role.getNo(), 
				roleMapper.todDetails(role), 60);

		return role.getNo();
	}

	@Transactional
	@Override
	public long modifyRoleState(long roleNo, @NotNull State state) {

		// 권한 정보 조회 - 다른 트랜잭션이 접근 불가 (배타적 잠금)
		RoleEntity role = roleRepository.findByIdForUpdate(roleNo)
				.orElseThrow(() -> new ResourceNotFoundException("권한이 존재하지 않습니다."));

		// 권한 상태값 변경
		role.setState(state);

		// 활성화 여부에 따라 캐시 저장 혹은 삭제
		String cacheKey = CacheKeys.ROLE_KEY + role.getNo();
		if (state == State.ACTIVE) {
			redisService.caching(cacheKey, 
					roleMapper.todDetails(role), 60);
		} else {
			redisService.remove(cacheKey);
		}
		
		return role.getNo();
	}

	@Transactional(readOnly = true)
	@Override
	public RoleDetails getRole(long roleNo) {

		// 캐시키와 타입 정의 
		String cacheKey = CacheKeys.ROLE_KEY + roleNo;
		TypeReference<RoleDetails> type = new TypeReference<RoleDetails>() {};

		// 캐시 데이터 조회 
		return redisService.get(cacheKey, type).orElseGet(() -> {

			// 캐시에 없을 경우 조회 
			RoleDetails details = roleMapper.todDetails(roleRepository.findById(roleNo)
					.orElseThrow(() -> new ResourceNotFoundException("권한이 존재하지 않습니다.")));

			// 활성화 상태가 아닐 경우 오류 발생 
			if (details.getState() == State.DEACTIVE)
				throw new ResourceDeactiveException("비활성화된 권한입니다.");
				
			// 조회한 데이터 캐시 저장 
			redisService.caching(cacheKey, details, 60);

			return details;
		});
	}

	private BooleanExpression[] getBooleanExpressions(RoleSearchCond cond) {
		return new BooleanExpression[] {
				QueryDslUtils.containsIfPresent(TB_ROLE.name, cond.getName()),
				QueryDslUtils.equalsIfPresent(TB_ROLE.accessMethod, cond.getAccessMethod()),
				QueryDslUtils.containsIfPresent(TB_ROLE.accessUrlPattern, cond.getAccessUrlPattern()),
				QueryDslUtils.betweenIfPresent(TB_ROLE.createdDate, 
						LocalDateTime.of(cond.getFrom(), LocalTime.of(0, 0, 0)), 
						LocalDateTime.of(cond.getTo(), LocalTime.of(23, 59, 59))),
				QueryDslUtils.equalsIfPresent(TB_ROLE.state, State.ACTIVE)
		};
	}
	
	private OrderSpecifier<?> getOrderSpecifier(RoleSearchCond cond) {

		RoleSortBy sortBy = cond.getSortBy();
		OrderBy orderBy = cond.getOrderBy();
		
		ComparableExpressionBase<?> sortCol = null;

		// 정렬에 사용할 컬럼 지정 
		if (sortBy == RoleSortBy.NAME) {
			sortCol = TB_ROLE.name;
		} else if (sortBy == RoleSortBy.METHOD) {
			sortCol = TB_ROLE.accessMethod;
		} else if (sortBy == RoleSortBy.URL_PATTERN) {
			sortCol = TB_ROLE.accessUrlPattern;
		} else if (sortBy == RoleSortBy.CREATED_DATE) {
			sortCol = TB_ROLE.createdDate;
		} else if (sortBy == RoleSortBy.UPDATED_DATE) {
			sortCol = TB_ROLE.updatedDate;
		} else {
			sortCol = TB_ROLE.no;
		}
		
		// 정렬 방식 지정  
		return (orderBy == OrderBy.DESC) 
				? sortCol.desc()
				: sortCol.asc();
	} 
	
	@Transactional(readOnly = true)
	@Override
	public PagingList<RoleDetails> searchRoleList(@NotNull @Valid RoleSearchCond cond) {

		// 조회 조건 및 정렬 조건 생성 
		BooleanExpression[] queryConditions = getBooleanExpressions(cond);
		OrderSpecifier<?> orderCondition = getOrderSpecifier(cond);

		// 페이징을 위한 Offset 
		long offset = (cond.getPage() - 1) * cond.getPageRows();
		if (offset < 0) offset = 0;

		// 조건에 해당하는 권한의 총 개수 
		long totalCount = jpaQueryFactory
				.select(TB_ROLE.count())
				.from(TB_ROLE)
				.where(queryConditions)
				.fetchOne();

		// 조건에 해당하는 권한 목록 조회 - 페이징
		List<RoleDetails> list = jpaQueryFactory
				.selectFrom(TB_ROLE)
				.where(queryConditions)
				.orderBy(orderCondition)
				.offset(offset).limit(cond.getPageRows())
				.fetch().stream()
				.map(x -> roleMapper.todDetails(x))
				.collect(Collectors.toList());

		return new PagingList<RoleDetails>(list, cond, totalCount);
	}

}
