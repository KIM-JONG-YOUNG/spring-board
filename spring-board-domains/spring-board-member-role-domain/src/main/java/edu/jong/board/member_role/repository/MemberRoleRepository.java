package edu.jong.board.member_role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.jong.board.member_role.entity.MemberRoleEntity;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, MemberRoleEntity.PK> {

	
}
