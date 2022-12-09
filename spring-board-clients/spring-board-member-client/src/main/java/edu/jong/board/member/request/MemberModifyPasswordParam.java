package edu.jong.board.member.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberModifyPasswordParam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "현재 비밀번호", defaultValue = "1234")
	@NotBlank
	private String curPassword;

	@Schema(description = "신규 비밀번호", defaultValue = "12345")
	@NotBlank
	private String newPassword;

}
