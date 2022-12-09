package edu.jong.board.redis.exception;

public class ConvertTypeToCacheDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConvertTypeToCacheDataException(String type) {
		super(String.format("%s을 캐시 데이터(Json)로 변환하는 과정에서 오류가 발생했습니다.", type));
	}
}
