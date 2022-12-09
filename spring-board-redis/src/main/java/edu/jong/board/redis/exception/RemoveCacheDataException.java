package edu.jong.board.redis.exception;

public class RemoveCacheDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RemoveCacheDataException(String cacheKey) {
		super(String.format("'%s'의 캐시 데이터를 삭제하는 과정에서 오류가 발생했습니다.", cacheKey));
	}
}
