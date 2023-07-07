package com.alves.calendar.entities;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Holiday {

	@JsonFormat(pattern = "dd-MM-yyyy")
	private String date;
	private String description;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
