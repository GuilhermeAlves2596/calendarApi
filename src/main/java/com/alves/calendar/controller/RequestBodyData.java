package com.alves.calendar.controller;

public class RequestBodyData {
	
    private String date;
    private String newDate;
    private String newDescription;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	public String getNewDescription() {
		return newDescription;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}
    
}

