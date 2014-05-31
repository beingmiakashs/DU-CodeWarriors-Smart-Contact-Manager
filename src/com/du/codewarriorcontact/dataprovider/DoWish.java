package com.du.codewarriorcontact.dataprovider;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.telephony.SmsManager;

public class DoWish {
	public String contactNumber;
	public Date wishDate ;
	public String msg ;
	
	public boolean Check(){
		Calendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    Date today = c.getTime(); 
		if (wishDate.getDate() == today.getDate())
			return true;
		return false;
	}
	
	public void SendWish(){
		if (Check()){
			try {
	            // Get the default instance of the SmsManager
	            SmsManager smsManager = SmsManager.getDefault();
	            smsManager.sendTextMessage(contactNumber,
	                    null, 
	                    msg,
	                    null,
	                    null);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}
	}
}
