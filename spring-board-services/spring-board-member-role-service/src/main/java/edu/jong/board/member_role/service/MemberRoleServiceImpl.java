package edu.jong.board.member_role.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.jong.board.client.exception.ResourceNotFoundException;
import edu.jong.board.common.BoardConstants.CacheKeys;
import edu.jong.board.member.entity.MemberEntity;
import edu.jong.board.member.entity.QMemberEntity;
import edu.jong.board.member.entity.QRoleEntity;
import edu.jong.board.member.entity.RoleEntity;
import edu.jong.board.member.repository.MemberRepository;
import edu.jong.board.member.repository.RoleRepository;
import edu.jong.board.member_role.entity.MemberRoleEntity;
import edu.jong.board.member_role.entity.QMemberRoleEntity;
import edu.jong.board.member_role.mapper.MemberRoleMapper;
import edu.jong.board.member_role.repository.MemberRoleRepository;
import edu.jong.board.member_role.response.MemberRoleDetails;
import edu.jong.board.redis.service.RedisService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberRoleServiceImpl implements MemberRoleService {

	private final MemberRoleMapper memberRoleMapper;
	private final RoleRepository roleRepository;
	private final MemberRepository memberRepository;
	private final MemberRoleRepository memberRoleRepository;
	
	private final RedisService redisService;
	
	private final JPAQueryFactory jpaQueryFactory;
	private final QRoleEntity TB_ROLE = QRoleEntity.roleEntity;
	private final QMemberEntity TB_MEMBER = QMemberEntity.memberEntity;
	private final QMemberRoleEntity TB_MEMBER_ROLE = QMemberRoleEntity.memberRoleEntity;
	
	@Transactional
	@Override
	public void addMemberRole(long memberNo, long roleNo) {

		// 사용자 조회 
		MemberEntity member = memberRepository.findById(memberNo)
				.orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다."));

		// 권한 조회 
		RoleEntity role = roleRepository.findById(roleNo)
				.orElseThrow(() -> new ResourceNotFoundException("권한이 존재하지 않습니다."));

		// 사용자에게 권한 부여 
		memberRoleRepository.save(MemberRoleEntity.builder()
				.member(member)
				.role(role)
				.build());
		
		// 캐시 저장 
		redisService.caching(
				CacheKeys.MEMBER_ROLE_LIST_KEY + memberNo, 
				getMemberRoleList(memberNo), 60);
	}

	@Transactional
	@Override
	public void removeMemberRole(long memberNo, long roleNo) {

		// 사용자 권한 삭제
		memberRoleRepository.delete(memberRoleRepository.findByIdForUpdate(memberNo, roleNo)
				.orElseThrow(() -> new ResourceNotFoundException("사용자에게 부여된 권한이 존재하지 않습니다.")));
	
		// 캐시 삭제 
		redisService.remove(CacheKeys.MEMBER_ROLE_LIST_KEY + memberNo);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MemberRoleDetails> getMemberRoleList(long memberNo) {
		
		// 캐시키와 타입 정의 
		String cacheKey = CacheKeys.MEMBER_ROLE_LIST_KEY + memberNo;
		TypeReference<List<MemberRoleDetails>> type = 
				new TypeReference<List<MemberRoleDetails>>() {};

		// 캐시 데이터 조회 
		return redisService.get(cacheKey, type).orElseGet(() -> {

			// 캐시에 없을 경우 DB 조회 
			List<MemberRoleDetails> list = jpaQueryFactory.select(TB_ROLE)
					.from(TB_MEMBER_ROLE)
					.join(TB_MEMBER)
						.on(TB_MEMBER_ROLE.member.eq(TB_MEMBER),
							TB_MEMBER_ROLE.member.no.eq(memberNo))
					.join(TB_ROLE)
						.on(TB_MEMBER_ROLE.role.eq(TB_ROLE))
					.fetch().stream()
					.map(x -> memberRoleMapper.toDetails(x))
					.collect(Collectors.toList());

			// 조회한 데이터 캐시 저장 
			redisService.caching(cacheKey, list, 60);

			return list;
		});
	}

}
