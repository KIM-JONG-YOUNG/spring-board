package edu.jong.board.member.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.type.State;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberPasswordModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;

@FeignClient("member-service")
public interface MemberServiceClient {

	@Operation(summary = "사용자 생성")
	@PostMapping(value = "/members",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> addMember(
			@RequestBody MemberAddParam param);

	@Operation(summary = "사용자 수정")
	@PutMapping(value = "/members/{memberNo}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> modifyMember(
			@PathVariable long memberNo,
			@RequestBody MemberModifyParam param);

	@Operation(summary = "사용자 비밀번호 수정")
	@PutMapping(value = "/members/{memberNo}/password",
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> modifyMemberPassword(
			@PathVariable long memberNo,
			@RequestBody MemberPasswordModifyParam param);

	@Operation(summary = "사용자 상태 변경")
	@PutMapping(value = "/members/{memberNo}/data-state",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> modifyMemberState(
			@PathVariable long memberNo,
			@RequestBody State state);

	@Operation(summary = "사용자 조회")
	@GetMapping(value = "/members/{memberNo}")
	ResponseEntity<MemberDetails> getMember(
			@PathVariable long memberNo);

	@Operation(summary = "사용자 검색")
	@GetMapping(value = "/members/search")
	ResponseEntity<PagingList<MemberDetails>> searchMemberList(
			MemberSearchCond cond);

	
}
