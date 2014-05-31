package com.du.codewarriorcontact.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.model.Sms;

public class MessageHistoryDataProvider {
	
	private Activity activity;
	
	public ArrayList<Sms> displaySMS() {
		
		activity = MainActivity.activity;

    	ArrayList<Sms> cList = new ArrayList<Sms>() ;
    	 Uri uriSMSURI = Uri.parse("content://sms/inbox");
         Cursor cur = activity.getContentResolver().query(uriSMSURI, null, null, null,"date DESC");
         
         while (cur.moveToNext()) {
         	
             
         	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
         	Calendar calendar = Calendar.getInstance();
         	long ms = cur.getLong(5);
         	calendar.setTimeInMillis(ms);
         	Sms c = new Sms() ;
    		c.sender = cur.getString(2) ;
    		c.sms = cur.getString(13) ;
    		c.date = formatter.format(calendar.getTime()); 
            cList.add(c);
         }
         
         return cList ;
   }


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
