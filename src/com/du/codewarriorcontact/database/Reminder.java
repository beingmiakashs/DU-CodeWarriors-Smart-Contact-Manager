package com.du.codewarriorcontact.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "reminder")
public class Reminder {

	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String contact_number;
	@DatabaseField(dataType = DataType.DATE_LONG)
    private Date reminder_date;
	@DatabaseField
    private String destination;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public Reminder() {
        // ORMLite needs a no-arg constructor 
    }
    public Reminder(String contact_number,
    				Date reminder_date,
    				String destination) {
    	this.contact_number = contact_number;
    	this.reminder_date = reminder_date;
    	this.destination = destination;
    }
    
    //================================================================================
    // Accessors
    //================================================================================
    public int getRowId() {
        return row_id;
    }
    public void setRowId(int id) {
        this.row_id = id;
    }
    
    public String getContactNumber() {
        return contact_number;
    }
    public void setContactNumber(String contactNumber) {
        this.contact_number = contactNumber;
    }
    
    public Date getReminderDate() {
        return reminder_date;
    }
    public void setReminderDate(Date date) {
        this.reminder_date = date;
    }
    
    public String getDestination() {
        return destination;
    }
    public void setDestination(String dest) {
        this.destination = dest;
    }
}
