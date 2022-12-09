package edu.jong.board.member.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.jong.board.common.BoardConstants;
import edu.jong.board.common.type.Gender;
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
public class MemberDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "계정")
	private String username;
	
	@Schema(description = "이름")
	private String name;
	
	@Schema(description = "성별")
	private Gender gender;
	
	@Schema(description = "이메일")
	private String email;

	@Schema(description = "생성일시")
	@JsonFormat(pattern = BoardConstants.DATE_TIME_PATTERN)
	private LocalDateTime createdDate;
	
	@Schema(description = "수정일시")
	@JsonFormat(pattern = BoardConstants.DATE_TIME_PATTERN)
	private LocalDateTime updatedDate;

	@Schema(description = "활성여부")
	private State state;

}
