package edu.jong.board.member_role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import edu.jong.board.member.entity.QRoleEntity;
import edu.jong.board.member.entity.RoleEntity;
import edu.jong.board.member_role.response.MemberRoleDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
		unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberRoleMapper {

	public static final QRoleEntity TB_ROLE = QRoleEntity.roleEntity;
	
	MemberRoleDetails toDetails(RoleEntity entity);
	
}
