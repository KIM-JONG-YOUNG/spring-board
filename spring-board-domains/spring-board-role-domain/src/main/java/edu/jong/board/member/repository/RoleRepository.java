package edu.jong.board.member.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.jong.board.member.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	boolean existsByName(String name);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT R FROM RoleEntity R WHERE R.no = :roleNo")
	Optional<RoleEntity> findByIdForUpdate(
			@Param("roleNo") long roleNo);
	
}
