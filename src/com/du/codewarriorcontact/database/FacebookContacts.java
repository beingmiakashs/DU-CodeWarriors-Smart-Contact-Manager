package com.du.codewarriorcontact.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "facebook_contacts")
public class FacebookContacts {

	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String fid;
	@DatabaseField
    private String first_name;
	@DatabaseField
    private String last_name;
	@DatabaseField
    private String email;
	@DatabaseField
    private String phone_number;
	@DatabaseField
    private String picture_url;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public FacebookContacts() {
        // ORMLite needs a no-arg constructor 
    }
    public FacebookContacts(String fid,
    						String firstName,
    						String lastName,
    						String email,
    						String phoneNumber,
    						String pictureUrl) {
    	this.fid = fid;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.phone_number = phoneNumber;
		this.picture_url = pictureUrl;
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
    
    public String getFid() {
        return fid;
    }
    public void setFid(String id) {
        this.fid = id;
    }
    
    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }
    
    public String getLastName() {
        return last_name;
    }
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phone_number;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }
    
    public String getPictureUrl() {
        return picture_url;
    }
    public void setPictureUrl(String url) {
        this.picture_url = url;
    }
}
