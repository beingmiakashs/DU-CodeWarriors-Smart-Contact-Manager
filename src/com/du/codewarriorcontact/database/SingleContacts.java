package com.du.codewarriorcontact.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "single_contacts")
public class SingleContacts {

	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String cid;
	@DatabaseField
    private String gid;
	@DatabaseField
    private String fid;
	@DatabaseField
    private int is_emergency;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public SingleContacts() {
        // ORMLite needs a no-arg constructor 
    }
    public SingleContacts(	String cid,
    						String gid,
    						String fid) {
    	this.cid = cid;
		this.gid = gid;
		this.fid = fid;
		this.is_emergency = 0;
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
    
    public String getCid() {
        return cid;
    }
    public void setCid(String id) {
        this.cid = id;
    }
    
    public String getFid() {
        return fid;
    }
    public void setFid(String id) {
        this.fid = id;
    }
    
    public String getGid() {
        return gid;
    }
    public void setGid(String id) {
        this.gid = id;
    }
    
    public int getIsEmergency() {
        return is_emergency;
    }
    public void setIsEmergency(int b) {
        this.is_emergency = b;
    }
}
