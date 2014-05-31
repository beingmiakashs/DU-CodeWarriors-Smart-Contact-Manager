package com.du.codewarriorcontact.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "wish_list")
public class WishList {

	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String message;
	@DatabaseField
    private String contact_number;
	@DatabaseField(dataType = DataType.DATE_LONG)
    private Date date;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public WishList() {
        // ORMLite needs a no-arg constructor 
    }
    public WishList(String message,
    				String contactNumber,
    				Date date) {
    	this.message = message;
    	this.contact_number = contactNumber;
    	this.date = date;
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
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg) {
        this.message = msg;
    }
}
