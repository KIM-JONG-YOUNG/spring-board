package edu.jong.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.jong.board.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
