package edu.jong.board.member_role.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.member_role.client.MemberRoleServiceClient;
import edu.jong.board.member_role.response.MemberRoleDetails;
import edu.jong.board.member_role.service.MemberRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberRoleController implements MemberRoleServiceClient {

	private final MemberRoleService memberRoleService;
	
	@Override
	public ResponseEntity<Void> addMemberRole(long memberNo, long roleNo) {
		
		log.info("Add Member Role Parameter => memberNo={}, roleNo={}", memberNo, roleNo);
		
		memberRoleService.addMemberRole(memberNo, roleNo);
		
		String url = "/member-roles/" + memberNo;
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> removeMemberRole(long memberNo, long roleNo) {

		log.info("Remove Member Role Parameter => memberNo={}, roleNo={}", memberNo, roleNo);

		memberRoleService.removeMemberRole(memberNo, roleNo);;

		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Override
	public ResponseEntity<List<MemberRoleDetails>> getMemberRoleList(long memberNo) {

		log.info("Get Member Role List Parameter => memberNo={}", memberNo);

		return ResponseEntity.status(HttpStatus.OK)
				.body(memberRoleService.getMemberRoleList(memberNo));
	}

}
