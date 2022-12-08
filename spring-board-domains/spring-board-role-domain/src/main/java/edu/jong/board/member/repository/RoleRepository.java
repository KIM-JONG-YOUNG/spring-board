package edu.jong.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.jong.board.member.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
