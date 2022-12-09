package edu.jong.board.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import edu.jong.board.common.type.OrderBy;
import edu.jong.board.common.type.RoleSortBy;
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
	
	RoleDetails todDetails(RoleEntity entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	RoleEntity updateEntity(RoleModifyParam param, @MappingTarget RoleEntity entity);
	
	default OrderSpecifier<?> toOrderSpecifier(RoleSortBy sortBy, OrderBy orderBy) {

		ComparableExpressionBase<?> sortCol = null;

		if (sortBy == RoleSortBy.NAME) {
			sortCol = TB_ROLE.name;
		} else if (sortBy == RoleSortBy.METHOD) {
			sortCol = TB_ROLE.accessMethod;
		} else if (sortBy == RoleSortBy.URL_PATTERN) {
			sortCol = TB_ROLE.accessUrlPattern;
		} else if (sortBy == RoleSortBy.CREATED_DATE) {
			sortCol = TB_ROLE.createdDate;
		} else if (sortBy == RoleSortBy.UPDATED_DATE) {
			sortCol = TB_ROLE.updatedDate;
		} else {
			sortCol = TB_ROLE.no;
		}
		
		return (orderBy == OrderBy.DESC) 
				? sortCol.desc()
				: sortCol.asc();
	} 
}
