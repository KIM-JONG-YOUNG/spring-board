package edu.jong.board.role.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import edu.jong.board.common.type.State;
import edu.jong.board.role.client.RoleServiceClient;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RoleController implements RoleServiceClient {

	@Override
	public ResponseEntity<Void> addRole(RoleAddParam param) {
		// TODO Auto-generated method stub
		log.info("addPost({})", param);
		return null;
	}

	@Override
	public ResponseEntity<Void> modifyRole(long roleNo, RoleModifyParam param) {
		// TODO Auto-generated method stub 
		log.info("modifyRole({}, {})", roleNo, param);
		return null;
	}

	@Override
	public ResponseEntity<Void> modifyRoleState(long roleNo, State state) {
		// TODO Auto-generated method stub
		log.info("modifyRoleState({}, {})", roleNo, state);
		return null;
	}

	@Override
	public ResponseEntity<RoleDetails> getRole(long roleNo) {
		// TODO Auto-generated method stub
		log.info("getRole({})", roleNo);
		return null;
	}

	@Override
	public ResponseEntity<RoleDetails> searchRoleList(RoleSearchCond cond) {
		// TODO Auto-generated method stub
		log.info("searchRoleList({})", cond);
		return null;
	}

}
