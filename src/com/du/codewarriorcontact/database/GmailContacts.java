package com.du.codewarriorcontact.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "gmail_contacts")
public class GmailContacts {

	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String gid;
	@DatabaseField
    private String first_name;
	@DatabaseField
    private String last_name;
	@DatabaseField
    private String email;
	@DatabaseField
    private String picture_url;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public GmailContacts() {
        // ORMLite needs a no-arg constructor 
    }
    public GmailContacts(	String gid,
    						String firstName,
    						String lastName,
    						String email,
    						String pictureUrl) {
    	this.gid = gid;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
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
    
    public String getGid() {
        return gid;
    }
    public void setGid(String id) {
        this.gid = id;
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
    
    public String getPictureUrl() {
        return picture_url;
    }
    public void setPictureUrl(String url) {
        this.picture_url = url;
    }
}
