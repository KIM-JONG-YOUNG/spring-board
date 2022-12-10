package edu.jong.board.member_role.response;

import java.io.Serializable;

import edu.jong.board.common.type.Method;
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
public class MemberRoleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "권한명")
	private String name;

	@Schema(description = "접근 가능한 HTTP Method")
	private Method accessMethod;

	@Schema(description = "접근 가능한 URL 패턴")
	private String accessUrlPattern;

}
