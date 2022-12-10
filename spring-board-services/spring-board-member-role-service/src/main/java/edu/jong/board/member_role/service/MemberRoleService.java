package edu.jong.board.member_role.service;

import java.util.List;

import edu.jong.board.member_role.response.MemberRoleDetails;

public interface MemberRoleService {

	void addMemberRole(long memberNo, long roleNo);

	void removeMemberRole(long memberNo, long roleNo);

	List<MemberRoleDetails> getMemberRoleList(long memberNo);

}
