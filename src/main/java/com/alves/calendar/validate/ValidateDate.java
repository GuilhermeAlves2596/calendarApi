package com.alves.calendar.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

@Service
public class ValidateDate {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
    public void validateDay(String date) {
        try {
            LocalDate day = LocalDate.parse(date, formatter);

            if (day.getDayOfMonth() < 1 || day.getDayOfMonth() > 31) {
                throw new IllegalArgumentException();
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida");
        }
    }
    
    public void validateMonth(String date) {
        try {
            LocalDate day = LocalDate.parse(date, formatter);

            int month = day.getMonthValue();
            int dayOfMonth = Integer.parseInt(date.substring(0, 2));
            if (dayOfMonth == 31){
            	if(month == 4 || month == 6 || month == 9 || month == 11){
            		throw new IllegalArgumentException("Data inválida. Este mês não possui o dia 31.");            		
            	}
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Mês invalido.");
        }
    }
    
    public void validateLeapYear(String date) {
    	try {
    		LocalDate data = LocalDate.parse(date, formatter);
    		
    		int day = Integer.parseInt(date.substring(0, 2));
    		int month = data.getMonthValue();
    		int year = data.getYear();
    		
    		if((year % 4 != 0) && month == 2 && day == 29) {
    			throw new IllegalArgumentException("Data inválida. Este ano não é bissexto.");
    		}
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Ano invalido.");
		}
    }
}