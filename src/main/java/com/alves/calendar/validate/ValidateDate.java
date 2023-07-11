package com.alves.calendar.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

@Service
public class ValidateDate {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	// Validação do dia, mes e ano
    public void validateDay(String date) {
        try {
            LocalDate data = LocalDate.parse(date, formatter);
            int day = data.getDayOfMonth();
    		int month = data.getMonthValue();
    		int year = data.getYear();

            if ((day < 1 || day > 31) || month < 1 || month > 12 || year < 1) {
                throw new IllegalArgumentException();
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida.");
        }
    }
    
    // Validação se o mes possui apenas 30 dias
    public void validateMonth(String date) {
        try {
            LocalDate day = LocalDate.parse(date, formatter);

            int month = day.getMonthValue();
            int dayOfMonth = Integer.parseInt(date.substring(0, 2));
            if ((dayOfMonth == 31) || month == 2 && dayOfMonth >= 30) {
            	if(month == 4 || month == 6 || month == 9 || month == 11 || month == 2){
            		throw new IllegalArgumentException("Data inválida.");            		
            	}
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    
    // Validação de ano bissexto
    public void validateLeapYear(String date) {
    	try {
    		LocalDate data = LocalDate.parse(date, formatter);
    		
    		int day = Integer.parseInt(date.substring(0, 2));
    		int month = data.getMonthValue();
    		int year = data.getYear();
    		
    		if((year % 4 != 0) && month == 2 && day >= 29) {
    			throw new IllegalArgumentException("Data inválida. Este ano não é bissexto.");
    		}
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
    }
}