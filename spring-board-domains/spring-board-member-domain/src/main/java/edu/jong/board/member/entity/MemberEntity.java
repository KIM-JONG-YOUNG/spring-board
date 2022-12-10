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
import edu.jong.board.common.type.Gender;
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
@Table(name = TableNames.TB_MEMBER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class MemberEntity extends BaseEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_no", 
			nullable = false, 
			unique = true)
	private long no;
	
	@Column(name = "member_username", 
			length = 30,
			nullable = false, 
			unique = true)
	private String username;
	
	@Setter
	@Column(name = "member_password", 
			length = 60,
			nullable = false)
	private String password;
	
	@Setter
	@Column(name = "member_name", 
			length = 30,
			nullable = false)
	private String name;
	
	@Setter
	@Convert(converter = GenderAttributeConverter.class)
	@Column(name = "member_gender", 
			length = 1,
			nullable = false)
	private Gender gender;
	
	@Setter
	@Column(name = "member_email", 
			length = 60,
			nullable = false)
	private String email;
	
	@Builder
	public MemberEntity(String username, String password, String name, Gender gender, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.email = email;
	}

	@Converter
	public static class GenderAttributeConverter extends AbstractAttributeConverter<Gender, Character> {
		
		public GenderAttributeConverter() {
			super(Gender.class, false);
		}
		
	}
	
}
