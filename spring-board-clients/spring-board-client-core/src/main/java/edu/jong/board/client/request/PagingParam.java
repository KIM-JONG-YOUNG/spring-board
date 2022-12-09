package edu.jong.board.client.request;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PagingParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "페이지", defaultValue = "1")
	@Builder.Default
	private long page = 1;
	
	@Schema(description = "페이지 별 목록의 개수", defaultValue = "10")
	@Builder.Default
	private long pageRows = 10;
	
	@Schema(description = "페이지 그룹의 페이지의 개수", defaultValue = "10")
	@Builder.Default
	private long pageGroupSize = 10;
	
}
