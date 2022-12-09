package edu.jong.board.client.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.jong.board.common.BoardConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "오류 클래스")
	private Class<? extends Exception> errorClass;

	@Schema(description = "오류 메시지")
	private String message;
	
	@Schema(description = "오류 발생 일시")
	@JsonFormat(pattern = BoardConstants.DATE_TIME_PATTERN)
	private LocalDateTime timestamp;

	public ErrorResponse(Exception e) {
		super();
		this.errorClass = e.getClass();
		this.message = e.getMessage();
		this.timestamp = LocalDateTime.now();
	}
	
}
