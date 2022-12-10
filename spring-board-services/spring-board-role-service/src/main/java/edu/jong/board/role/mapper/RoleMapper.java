package edu.jong.board.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import edu.jong.board.member.entity.QRoleEntity;
import edu.jong.board.member.entity.RoleEntity;
import edu.jong.board.role.request.RoleAddParam;
import edu.jong.board.role.request.RoleModifyParam;
import edu.jong.board.role.response.RoleDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
		unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

	public static final QRoleEntity TB_ROLE = QRoleEntity.roleEntity;
	
	RoleEntity toEntity(RoleAddParam param);
	
	RoleDetails toDetails(RoleEntity entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	RoleEntity updateEntity(RoleModifyParam param, @MappingTarget RoleEntity entity);
	
}
