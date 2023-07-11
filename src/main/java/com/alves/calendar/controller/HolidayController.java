package com.alves.calendar.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alves.calendar.entities.Holiday;
import com.alves.calendar.validate.ValidateDate;
import com.alves.calendar.validate.Validates;

@RestController
@RequestMapping("/holidays")
public class HolidayController {
	
	@Autowired
	private ValidateDate validateDate;
	@Autowired
	private Validates validate;
	
    @SuppressWarnings("unused")
	private RedisTemplate<String, Holiday> redisTemplate;
    private HashOperations<String, String, Holiday> hashOperations;

    public HolidayController(RedisTemplate<String, Holiday> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }
    
    // Criar uma data
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHoliday(@RequestBody Holiday holiday) {
        try {
            validateDate.validateDay(holiday.getDate());
            validateDate.validateMonth(holiday.getDate());
            validateDate.validateLeapYear(holiday.getDate());
            
            validate.dateExists(holiday.getDate());
            
            hashOperations.put("holidays", holiday.getDate(), holiday);
            return ResponseEntity.ok("Data criada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    
    // Pesquisar uma data
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHoliday(@RequestBody Holiday holiday) {
        if (!validate.isExists(holiday.getDate())) {
            return ResponseEntity.ok().body("Data não encontrada");
        }
        
        String date = holiday.getDate();
        return ResponseEntity.ok().body(hashOperations.get("holidays", date));
    }
    
    
    // Listar todas as datas
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
    	if(validate.bdEmpty()) {
    		return ResponseEntity.ok().body("Não há datas cadastradas");
    	}
    	
        Map<String, Holiday> holidayMap = hashOperations.entries("holidays");
        return ResponseEntity.ok().body(new ArrayList<>(holidayMap.values()));
    }

    // Alterar uma data
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHoliday(@RequestBody RequestBodyData bodyData) {
        String dateToBeUpdated = bodyData.getDate();
        String newDate = bodyData.getNewDate();
        String newDescription = bodyData.getNewDescription();

        Holiday holiday = hashOperations.get("holidays", dateToBeUpdated);
        if (holiday != null) {
            holiday.setDate(newDate);
            holiday.setDescription(newDescription);
            hashOperations.put("holidays", newDate, holiday);
            hashOperations.delete("holidays", dateToBeUpdated);
            
            return ResponseEntity.ok("Data alterada com sucesso");
        } else {
        	return ResponseEntity.ok("Data não encontrada");
        }
    }

    // Deletar uma data
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteHoliday(@RequestBody RequestBodyData bodyData) {
        String date = bodyData.getDate();
        if (!validate.isExists(date)) {
            return ResponseEntity.ok().body("Data não encontrada");
        }

        hashOperations.delete("holidays", date);
        return ResponseEntity.ok().body("Data excluida com sucesso");
    }
}
