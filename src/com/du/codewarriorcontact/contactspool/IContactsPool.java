package com.du.codewarriorcontact.contactspool;

import android.content.Context;

import com.du.codewarriorcontact.model.Contact;

public interface IContactsPool {

	public void SyncAllContact(Context ctx);
	public int CreateContact(Context ctx, Contact ct) ;
	public void DeleteContact(Context ctx, int id);
}
