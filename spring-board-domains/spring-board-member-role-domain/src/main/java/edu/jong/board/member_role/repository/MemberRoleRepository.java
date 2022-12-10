package edu.jong.board.member_role.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jong.board.member_role.entity.MemberRoleEntity;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, MemberRoleEntity.PK> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT MR FROM MemberRoleEntity MR "
			+ "WHERE MR.member.no = :memberNo "
			+ "AND MR.role.no = :roleNo")
	Optional<MemberRoleEntity> findByIdForUpdate(
			@Param("memberNo") long memberNo,
			@Param("roleNo") long roleNo);
}
