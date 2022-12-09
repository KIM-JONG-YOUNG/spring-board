package edu.jong.board.client.request;

import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

public class PagingParamTests {

	@Getter
	@ToString(callSuper = true)
	@SuperBuilder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TestPagingParam extends PagingParam {
		
		private static final long serialVersionUID = 1L;

		private String prop;
		
	}
	
	@Test
	void createTest() {
		System.out.println(TestPagingParam.builder()
				.prop("value")
				.build());
	}
}
