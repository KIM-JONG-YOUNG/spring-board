package edu.jong.board.redis.exception;

public class ConvertCacheDataToTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConvertCacheDataToTypeException(String type) {
		super(String.format("캐시 데이터(Json)를 %s로 변환하는 과정에서 오류가 발생했습니다.", type));
	}
}
