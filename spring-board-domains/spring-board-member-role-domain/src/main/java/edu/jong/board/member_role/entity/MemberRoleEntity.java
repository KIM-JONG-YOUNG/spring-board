package edu.jong.board.member_role.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.jong.board.common.BoardConstants.TableNames;
import edu.jong.board.domain.entity.BaseEntity;
import edu.jong.board.member.entity.MemberEntity;
import edu.jong.board.member.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Entity
@IdClass(MemberRoleEntity.PK.class)
@Table(name = TableNames.TB_MEMBER_ROLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class MemberRoleEntity extends BaseEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no", nullable = false)
	private MemberEntity member;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_no", nullable = false)
	private RoleEntity role;

	@Builder
	public MemberRoleEntity(MemberEntity member, RoleEntity role) {
		super();
		this.member = member;
		this.role = role;
	}

	@Getter
	@Builder
	@ToString
	@EqualsAndHashCode
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static final class PK implements Serializable {

		private static final long serialVersionUID = 1L;
		private long member;
		private long role;
	
	}

}
