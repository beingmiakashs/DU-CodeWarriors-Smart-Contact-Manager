package com.du.codewarriorcontact.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Contact {
	
	public String id;
	public String firstName ;
	public String lastName ;
	public String phoneNumberMobile ;
	public String phoneNumberHome ;
	public String phoneNumberWork ;
	public String phoneNumberOther ;
	public String note ;
	public String address ;
	public String email ;
	public String company ;
	public String jobTitle ;
	public String group;
	public Bitmap photo;
	
	public Uri contentUri ;
	
	public Contact(){};
	
	public Contact(String id,
			String firstName,
			String lastName,
			String phoneNumberMobile,
			String phoneNumberHome,
			String phoneNumberWork,
			String phoneNumberOther,
			String note,
			String address,
			String email,
			String company,
			String jobTitle,
			Bitmap photo) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumberMobile = phoneNumberMobile;
		this.phoneNumberHome = phoneNumberHome;
		this.phoneNumberWork = phoneNumberWork;
		this.phoneNumberOther = phoneNumberOther;
		this.note = note;
		this.address = address;
		this.email = email;
		this.company = company;
		this.jobTitle = jobTitle;
		this.photo = photo;
	}
}
