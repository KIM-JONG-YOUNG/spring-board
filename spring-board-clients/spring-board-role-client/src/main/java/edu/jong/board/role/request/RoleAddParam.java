package edu.jong.board.role.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import edu.jong.board.common.type.Method;
import edu.jong.board.role.validate.AntPathPattern;
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
public class RoleAddParam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "권한명", defaultValue = "ROLE_TEST")
	@NotBlank
	@Size(max = 30)
	@Pattern(regexp = "^ROLE_[A-Z]+")
	private String name;

	@Schema(description = "접근 가능한 HTTP Method", defaultValue = "ALL")
	@NotNull
	private Method accessMethod;

	@Schema(description = "접근 가능한 URL 패턴", defaultValue = "http://localhost:8080/test/*")
	@NotBlank
	@Size(max = 60)
	@AntPathPattern
	private String accessUrlPattern;
	
}
