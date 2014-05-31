package com.du.codewarriorcontact;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;

import com.du.codewarriorcontact.contactspool.IContactsPool;

public class Utils {
	public static String AppFolder = "CWContacts" ;
	public static ArrayList<IContactsPool> poolList = new ArrayList<IContactsPool>() ;
	public static int SELECTED_ITEM = -1; 
	public static final boolean DEBUG = true;
	public static String ContactObject = "ContactObject"; 
	public static void print(String ss){
		if(DEBUG){
			Log.i("testing", ss);
		}
	}
	public static Bitmap retrieveContactPhoto(Activity ac, Long id) {
   	 
        Bitmap photo = null;
 
        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ac.getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id));
            	
            if (inputStream != null) {
            	
                photo = BitmapFactory.decodeStream(inputStream);
            }
            else
            {
            	return null;
            }
 
            assert inputStream != null;
            inputStream.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		return photo;
 
    }
}
