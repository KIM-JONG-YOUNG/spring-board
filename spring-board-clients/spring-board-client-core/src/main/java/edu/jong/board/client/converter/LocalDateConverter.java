package edu.jong.board.client.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import edu.jong.board.client.exception.ParameterLocalDateConvertException;
import edu.jong.board.common.BoardConstants;

public class LocalDateConverter implements Converter<String, LocalDate>{

	private final DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern(BoardConstants.DATE_PATTERN);
	
	@Override
	public LocalDate convert(String source) {
		
		if (StringUtils.isBlank(source)) return null;
		
		try {
			return LocalDate.parse(source, formatter);
		} catch (Exception e) {
			throw new ParameterLocalDateConvertException(
					String.format("날짜가 형식에 맞지 않습니다. (%s)", BoardConstants.DATE_PATTERN));
		}
	}

}
