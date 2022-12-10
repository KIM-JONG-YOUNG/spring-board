package edu.jong.board.member.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.client.response.ErrorResponse;
import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.type.State;
import edu.jong.board.member.client.MemberServiceClient;
import edu.jong.board.member.exception.PasswordNotMatchedException;
import edu.jong.board.member.request.MemberAddParam;
import edu.jong.board.member.request.MemberModifyParam;
import edu.jong.board.member.request.MemberPasswordModifyParam;
import edu.jong.board.member.request.MemberSearchCond;
import edu.jong.board.member.response.MemberDetails;
import edu.jong.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController implements MemberServiceClient {

	private final MemberService memberService;
	
	@Override
	public ResponseEntity<Void> addMember(MemberAddParam param) {

		log.info("Add Member Parameter => \n\t{}", param);

		String url = "/members/" + memberService.addMember(param);

		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> modifyMember(long memberNo, MemberModifyParam param) {

		log.info("Modify Member Parameter => \n\t{}\n\t{}", memberNo, param);

		String url = "/members/" + memberService.modifyMember(memberNo, param);

		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> modifyMemberPassword(long memberNo, MemberPasswordModifyParam param) {

		log.info("Modify Member Password Parameter => \n\t{}\n\t{}", memberNo, param);

		String url = "/members/" + memberService.modifyMemberPassword(memberNo, param);

		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> modifyMemberState(long memberNo, State state) {

		log.info("Modify Member State Parameter => \n\t{}\n\t{}", memberNo, state);

		String url = "/members/" + memberService.modifyMemberState(memberNo, state);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<MemberDetails> getMember(long memberNo) {

		log.info("Get Member Parameter => \n\t{}", memberNo);

		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.getMember(memberNo));
	}

	@Override
	public ResponseEntity<PagingList<MemberDetails>> searchMemberList(MemberSearchCond cond) {

		log.info("Search Member List Parameter => \n\t{}", cond);

		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.searchMemberList(cond));
	}

	@ExceptionHandler(PasswordNotMatchedException.class)
	public ResponseEntity<ErrorResponse> handlePasswordNotMatchedException(PasswordNotMatchedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(e));
	}
	
	
}
