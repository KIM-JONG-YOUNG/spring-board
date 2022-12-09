package edu.jong.board.client.response;

import java.util.List;

import edu.jong.board.client.request.PagingParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PagingList<T> {

	@Schema(description = "페이지 목록")
	private List<T> list;

	@Schema(description = "목록의 개수")
	private long count;
	
	@Schema(description = "현재 페이지")
	private long page;
	
	@Schema(description = "현재 페이지 그룹")
	private long pageGroup;
	
	@Schema(description = "현재 페이지 그룹 시작 페이지")
	private long pageGroupStart;
	
	@Schema(description = "현재 페이지 그룹 종료 페이지")
	private long pageGroupEnd;
	
	@Schema(description = "총 검색 결과")
	private long totalCount;
	
	@Schema(description = "총 페이지")
	private long totalPage;

	public PagingList(List<T> list, PagingParam param, long totalCount) {
		
		this.list = list;
		this.count = list.size();
		this.page = param.getPage();
		this.totalCount = totalCount;

		if (this.totalCount > 0) {
			
			this.totalPage = (long) Math.ceil((double) this.totalCount / param.getPageRows());
			this.pageGroup = (long) Math.ceil((double) this.totalPage / param.getPageGroupSize());

			this.pageGroupStart = this.pageGroup * param.getPageGroupSize() + 1;
			this.pageGroupEnd = this.pageGroup * param.getPageGroupSize();
			this.pageGroupEnd = (this.totalPage > this.pageGroupEnd) ? this.pageGroupEnd : this.totalPage;
		
		}
		
	}
	
}
