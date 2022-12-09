package edu.jong.board.client.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import edu.jong.board.common.BoardConstants;

public class LocalDateConverter implements Converter<String, LocalDate>{

	private final DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern(BoardConstants.DATE_PATTERN);
	
	@Override
	public LocalDate convert(String source) {
		return LocalDate.parse(source, formatter);
	}

}
