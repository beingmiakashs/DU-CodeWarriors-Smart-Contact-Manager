package com.du.codewarriorcontact.contactspool;

import java.util.ArrayList;

import android.content.Context;

import com.du.codewarriorcontact.database.FacebookContacts;
import com.du.codewarriorcontact.model.Contact;

public class FacebookContactsPool implements IContactsPool{
	/*
	 * Singleton
	 */
	private static FacebookContactsPool instance = null ;
	private FacebookContactsPool(){
		
	}
	
	public static FacebookContactsPool getInstance(){
		if (instance == null)
			instance = new FacebookContactsPool() ;
		return instance ;
	}
	
	 
	
	private ArrayList<FacebookContacts> fbList = new ArrayList<FacebookContacts>() ;
	
	public void SetData(ArrayList<FacebookContacts> fbList){
		
		
	}
	
	@Override
	public void SyncAllContact(Context ctx) {
		//
		// method
		//
		
		if (fbList.size() <= 0){
			// not synced wait for data or return;
			return ;
		}
		//update data
		
	}

	@Override
	public int CreateContact(Context ctx, Contact ct) {
		
		return 0;
	}

	@Override
	public void DeleteContact(Context ctx, int id) {
		// no facebook contact creates
		
	}
	

}
