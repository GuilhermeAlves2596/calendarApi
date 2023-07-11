package com.alves.calendar.validate;

import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.alves.calendar.entities.Holiday;

@Service
@RestController
public class Validates {

	private HashOperations<String, String, Holiday> hashOperations;

	public Validates(RedisTemplate<String, Holiday> redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}
	
	// Verica se já existe uma data cadastrada
	public void dateExists(String date) {
		try {
			Holiday findDate = hashOperations.get("holidays", date);
			if (findDate != null) {
				throw new IllegalArgumentException("Data já existente.");
			}
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	// Verifica se a data informada existe
	public boolean isExists(String date) {
	    try {
	        Holiday findDate = hashOperations.get("holidays", date);
	        return findDate != null;
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}
	
	//Verifica se o BD esta vazio
	public boolean bdEmpty() {
	    try {
	    	Map<String, Holiday> holidayMap = hashOperations.entries("holidays");
	        return holidayMap.isEmpty();
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException(e.getMessage());
	    }
	}

}
