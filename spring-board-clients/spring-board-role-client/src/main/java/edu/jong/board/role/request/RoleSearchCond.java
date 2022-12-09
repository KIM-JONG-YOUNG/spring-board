package edu.jong.board.role.request;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import edu.jong.board.client.request.PagingParam;
import edu.jong.board.common.type.Method;
import edu.jong.board.common.type.OrderBy;
import edu.jong.board.common.type.RoleSortBy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleSearchCond extends PagingParam {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "권한명 검색 키워드")
	@Size(max = 30)
	private String name;

	@Schema(description = "접근 가능한 HTTP Method 필터값")
	private Method accessMethod;

	@Schema(description = "접근 가능한 URL 패턴 검색 키워드")
	@Size(max = 60)
	private String accessUrlPattern;
	
	@Schema(description = "권한 범위 검색 시작일 (생성일 기준)")
	private LocalDate from;
	
	@Schema(description = "권한 범위 검색 종료일 (생성일 기준)")
	private LocalDate to;

	@Schema(description = "검색 결과 정렬 기준")
	private RoleSortBy sortBy;

	@Schema(description = "검색 결과 정렬 방식")
	private OrderBy orderBy;

}
