package edu.jong.board.member.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.type.State;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberPasswordModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;

@Validated
public interface MemberService {

	 long addMember(@NotNull @Valid MemberAddParam param);
	 
	 long modifyMember(long memberNo, @NotNull @Valid MemberModifyParam param);
	 
	 long modifyMemberPassword(long memberNo, @NotNull @Valid MemberPasswordModifyParam param);

	 long modifyMemberState(long memberNo, @NotNull State state);
	 
	 MemberDetails getMember(long memberNo);
	 
	 PagingList<MemberDetails> searchMemberList(@NotNull @Valid MemberSearchCond cond);
	 
}
