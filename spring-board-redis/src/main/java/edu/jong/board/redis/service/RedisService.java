package edu.jong.board.redis.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.jong.board.redis.exception.ConvertCacheDataToTypeException;
import edu.jong.board.redis.exception.ConvertTypeToCacheDataException;
import edu.jong.board.redis.exception.RemoveCacheDataException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void changeObjectMapper(ObjectMapper mapper) {
		this.objectMapper = mapper;
	}

	public void caching(String key, String value, long expireSeconds) {
		redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
	}

	public void  caching(String key, Object value, long expireSeconds) {

		String json = null;

		try {
			json = objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new ConvertTypeToCacheDataException(value.getClass().getTypeName());
		}

		if (json != null) redisTemplate.opsForValue().set(key, json, expireSeconds, TimeUnit.SECONDS);
	}

	public Optional<String> get(String key) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key));
	}

	public <T> Optional<T> get(String key, TypeReference<T> type) {

		String json = redisTemplate.opsForValue().get(key);

		if (StringUtils.isBlank(json)) return Optional.empty(); 
					
		T value = null;
		
		try {
			value = objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			throw new ConvertCacheDataToTypeException(type.getType().getTypeName());
		}

		return Optional.ofNullable(value);
	}

	public boolean has(String key) {
		return redisTemplate.hasKey(key);
	}

	public void remove(String key) {
		if (!redisTemplate.delete(key)) 
			throw new RemoveCacheDataException(key);
	}

}
