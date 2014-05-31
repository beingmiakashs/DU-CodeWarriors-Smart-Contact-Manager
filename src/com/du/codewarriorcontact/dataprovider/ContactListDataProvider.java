package com.du.codewarriorcontact.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.model.Contact;

public class ContactListDataProvider {
	
	private Activity activity;
	
	
    
    
    private Bitmap retrieveContactPhoto(Long id) {
    	 
        Bitmap photo = null;
 
        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(activity.getContentResolver(),
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
