package edu.jong.board.role.client;

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
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "권한", description = "권한 관리 API")
@FeignClient(name = "role-service")
public interface RoleServiceClient {

	@Operation(summary = "권한 생성")
	@PostMapping(value = "/roles",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> addRole(
			@RequestBody RoleAddParam param);

	@Operation(summary = "권한 수정")
	@PutMapping(value = "/roles/{roleNo}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> modifyRole(
			@PathVariable long roleNo,
			@RequestBody RoleModifyParam param);

	@Operation(summary = "권한 상태 변경")
	@PutMapping(value = "/roles/{roleNo}/data-state",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> modifyRoleState(
			@PathVariable long roleNo,
			@RequestBody State state);

	@Operation(summary = "권한 조회")
	@GetMapping(value = "/roles/{roleNo}")
	ResponseEntity<RoleDetails> getRole(
			@PathVariable long roleNo);

	@Operation(summary = "권한 검색")
	@GetMapping(value = "/roles/search")
	ResponseEntity<PagingList<RoleDetails>> searchRoleList(
			RoleSearchCond cond);

}
