package edu.jong.board.member_role.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.jong.board.member_role.response.MemberRoleDetails;

@FeignClient("member-role-service")
public interface MemberRoleServiceClient {

	@PostMapping(value = "/member-roles/{memberNo}/{roleNo}")
	ResponseEntity<Void> addMemberRole(
			@PathVariable long memberNo,
			@PathVariable long roleNo);

	@DeleteMapping(value = "/member-roles/{memberNo}/{roleNo}")
	ResponseEntity<Void> removeMemberRole(
			@PathVariable long memberNo,
			@PathVariable long roleNo);

	@GetMapping(value = "/member-roles/{memberNo}")
	ResponseEntity<List<MemberRoleDetails>> getMemberRoleList(
			@PathVariable long memberNo);

}
