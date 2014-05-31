package com.du.codewarriorcontact.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "phone_contacts")
public class PhoneContacts {
	
	@DatabaseField(generatedId = true)
    private int row_id;
	@DatabaseField
    private String cid;
	@DatabaseField
    private String first_name;
	@DatabaseField
    private String last_name;
	@DatabaseField
    private String email;
	@DatabaseField
    private String phone_number_mobile;
    @DatabaseField
    private String phone_number_home;
    @DatabaseField
    private String phone_number_work;
    @DatabaseField
    private String phone_number_other;
    @DatabaseField
    private String note;
    @DatabaseField
    private String address;
	@DatabaseField
    private String company;
	@DatabaseField
    private String job_title;
	@DatabaseField
    private String group;
	@DatabaseField
    private String picture_url;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public PhoneContacts() {
        // ORMLite needs a no-arg constructor 
    }
    public PhoneContacts(	String cid,
    						String firstName,
    						String lastName,
    						String email,
    						String phoneNumberMobile,
    						String phoneNumberHome,
    						String phoneNumberWork,
    						String phoneNumberOther,
    						String note,
    						String address,
    						String company,
    						String jobTitle,
    						String group,
    						String pictureUrl) {
    	this.cid = cid;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.phone_number_mobile = phoneNumberMobile;
		this.phone_number_home = phoneNumberHome;
		this.phone_number_work = phoneNumberWork;
		this.phone_number_other = phoneNumberOther;
		this.note = note;
		this.address = address;
		this.company = company;
		this.job_title = jobTitle;
		this.group = group;
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
    
    public String getCid() {
        return cid;
    }
    public void setCid(String id) {
        this.cid = id;
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
    
    public String getPhoneNumberMobile() {
        return phone_number_mobile;
    }
    public void setPhoneNumberMobile(String phoneNumberMobile) {
        this.phone_number_mobile = phoneNumberMobile;
    }

    public String getPhoneNumberHome() {
        return phone_number_home;
    }
    public void setPhoneNumberHome(String phoneNumberHome) {
        this.phone_number_home = phoneNumberHome;
    }
    
    public String getPhoneNumberWork() {
        return phone_number_work;
    }
    public void setPhoneNumberWork(String phoneNumberWork) {
        this.phone_number_work = phoneNumberWork;
    }
    
    public String getPhoneNumberOther() {
        return phone_number_other;
    }
    public void setPhoneNumberOther(String phoneNumberOther) {
        this.phone_number_other = phoneNumberOther;
    }
    
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getJobTitle() {
        return job_title;
    }
    public void setJobTitle(String jobTitle) {
        this.job_title = jobTitle;
    }
    
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    
    public String getPictureUrl() {
        return picture_url;
    }
    public void setPictureUrl(String url) {
        this.picture_url = url;
    }

}
