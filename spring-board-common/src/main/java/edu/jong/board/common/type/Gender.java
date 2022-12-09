package edu.jong.board.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements CodeEnum<Character> {

	MAIL('M'), FEMAIL('F');

	private final Character code;
	
}
