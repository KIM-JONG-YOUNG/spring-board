package edu.jong.board.member.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.jong.board.common.BoardConstants.TableNames;
import edu.jong.board.common.type.Method;
import edu.jong.board.domain.converter.AbstractAttributeConverter;
import edu.jong.board.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = TableNames.ROLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RoleEntity extends BaseEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_no", 
			nullable = false, 
			unique = true)
	private long no;
	
	@Column(name = "role_name", 
			length = 30, 
			nullable = false, 
			unique = true)
	private String name;

	@Setter
	@Convert(converter = MethodAttributeConverter.class)
	@Column(name = "role_access_method", 
			length = 1, 
			nullable = false)
	private Method accessMethod;

	@Setter
	@Column(name = "role_access_url_pattern", 
			length = 60, 
			nullable = false)
	private String accessUrlPattern;

	@Builder
	public RoleEntity(String name, Method accessMethod, String accessUrlPattern) {
		super();
		this.name = name;
		this.accessMethod = accessMethod;
		this.accessUrlPattern = accessUrlPattern;
	}

	@Converter
	public static class MethodAttributeConverter extends AbstractAttributeConverter<Method, String> {

		public MethodAttributeConverter() {
			super(Method.class, false);
		}
		
	}
	
}
