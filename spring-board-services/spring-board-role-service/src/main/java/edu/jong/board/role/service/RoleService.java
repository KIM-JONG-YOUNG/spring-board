package edu.jong.board.role.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import edu.jong.board.client.response.PagingList;
import edu.jong.board.common.type.State;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.request.RoleSearchCond;
import edu.jong.board.role.response.RoleDetails;

@Validated
public interface RoleService {

	long addRole(@NotNull @Valid RoleAddParam param);

	long modifyRole(long roleNo, @NotNull @Valid RoleModifyParam param);

	long modifyRoleState(long roleNo, @NotNull State state);

	RoleDetails getRole(long roleNo);

	PagingList<RoleDetails> searchRoleList(@NotNull @Valid RoleSearchCond cond);

}
