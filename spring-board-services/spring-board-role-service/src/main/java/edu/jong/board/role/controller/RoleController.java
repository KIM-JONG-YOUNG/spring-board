package edu.jong.board.role.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.type.State;
import edu.jong.board.role.client.RoleServiceClient;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import edu.jong.board.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoleController implements RoleServiceClient {

	private final RoleService roleService;
	
	@Override
	public ResponseEntity<Void> addRole(RoleAddParam param) {

		log.info("Add Role Parameter => \n\t{}", param);

		String url = "/roles/" + roleService.addRole(param);

		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> modifyRole(long roleNo, RoleModifyParam param) {

		log.info("Modify Role Parameter => \n\t{}\n\t{}", roleNo, param);
		
		String url = "/roles/" + roleService.modifyRole(roleNo, param);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<Void> modifyRoleState(long roleNo, State state) {

		log.info("Modify Role State Parameter => \n\t{}\n\t{}", roleNo, state);

		String url = "/roles/" + roleService.modifyRoleState(roleNo, state);

		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.header(HttpHeaders.LOCATION, url)
				.build();
	}

	@Override
	public ResponseEntity<RoleDetails> getRole(long roleNo) {

		log.info("Get Role Parameter => \n\t{}", roleNo);

		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.getRole(roleNo));
	}

	@Override
	public ResponseEntity<PagingList<RoleDetails>> searchRoleList(RoleSearchCond cond) {

		log.info("Search Role List Parameter => \n\t{}", cond);

		return ResponseEntity.status(HttpStatus.OK)
				.body(roleService.searchRoleList(cond));
	}

}
