package edu.jong.board.member.request;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import edu.jong.board.client.request.PagingParam;
import edu.jong.board.common.type.Gender;
import edu.jong.board.common.type.MemberSortBy;
import edu.jong.board.common.type.OrderBy;
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
public class MemberSearchCond extends PagingParam {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "계정 검색 키워드")
	@Size(max = 30)
	private String username;
	
	@Schema(description = "이름 검색 키워드")
	@Size(max = 30)
	private String name;
	
	@Schema(description = "성별 필터값")
	private Gender gender;
	
	@Schema(description = "이메일 검색 키워드")
	@Size(max = 60)
	private String email;
	
	@Schema(description = "사용자 범위 검색 시작일 (생성일 기준)")
	private LocalDate from;
	
	@Schema(description = "사용자 범위 검색 종료일 (생성일 기준)")
	private LocalDate to;

	@Schema(description = "사용자 결과 정렬 기준")
	private MemberSortBy sortBy;

	@Schema(description = "사용자 결과 정렬 방식")
	private OrderBy orderBy;

}
