package com.du.codewarriorcontact.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.model.CallEntry;

public class CallHistoryDataProvider {
	
	private Activity activity;
	
	public ArrayList<CallEntry> displayCallLog() {
		
		activity = MainActivity.activity;

    	ArrayList<CallEntry> cList = new ArrayList<CallEntry>() ;
    	StringBuffer sb = new StringBuffer();
    	  Cursor managedCursor = activity.managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, "date DESC");
    	  int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
    	  int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
    	  int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
    	  int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
    	  sb.append("Call Log :");
    	  while (managedCursor.moveToNext()) {
    	   String phNumber = managedCursor.getString(number);
    	   String callType = managedCursor.getString(type);
    	   String callDate = managedCursor.getString(date);
    	   Date callDayTime = new Date(Long.valueOf(callDate));
    	   String callDuration = managedCursor.getString(duration);
    	   String dir = null;
    	   int dircode = Integer.parseInt(callType);
    	   switch (dircode) {
    	   case CallLog.Calls.OUTGOING_TYPE:
    	    dir = "OUTGOING";
    	    break;

    	   case CallLog.Calls.INCOMING_TYPE:
    	    dir = "INCOMING";
    	    break;

    	   case CallLog.Calls.MISSED_TYPE:
    	    dir = "MISSED";
    	    break;
    	   }
    	   CallEntry s = new CallEntry();
    	   s.callDayTime=callDayTime;
    	   s.callDuration = callDuration;
    	   s.phNumber = phNumber;
    	   s.calltype=dir;
    	   cList.add(s);
    	   
    	  }
		return cList;

   }

}
