package edu.jong.board.role.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.jong.board.common.BoardConstants;
import edu.jong.board.common.type.Method;
import edu.jong.board.common.type.State;
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
public class RoleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "번호")
	private long no;

	@Schema(description = "권한명")
	private String name;

	@Schema(description = "접근 가능한 HTTP Method")
	private Method accessMethod;

	@Schema(description = "접근 가능한 URL 패턴")
	private String accessUrlPattern;

	@Schema(description = "생성일시")
	@JsonFormat(pattern = BoardConstants.DATE_TIME_PATTERN)
	private LocalDateTime createdDate;
	
	@Schema(description = "수정일시")
	@JsonFormat(pattern = BoardConstants.DATE_TIME_PATTERN)
	private LocalDateTime updatedDate;

	@Schema(description = "활성여부")
	private State state;

}
