package com.du.codewarriorcontact.dataprovider;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.du.codewarriorcontact.util.GPSTracker;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.GlobalLocation;

import android.location.Location;

public class DoReminder {
	String contactNumber;
	Date reminderDate ;
	String msg ;
	String destLocation ;
	private double sourceLat;
	private double sourceLon;
	
	
	
	
	public boolean Check(){
		Calendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    Date today = c.getTime(); 
		if (today.getTime() >= reminderDate.getTime())
			return true;
		
		GPSTracker gps = new GPSTracker(GlobalConstant.mContext);
		if (gps.canGetLocation()) {

			double latitudeFromTracker = gps.getLatitude();
			double longitudeFromTracker = gps.getLongitude();

			if (latitudeFromTracker < 1) {
				sourceLat = GlobalLocation.latitude;
				sourceLon = GlobalLocation.longitude;
			} else {
				GlobalLocation.latitude = latitudeFromTracker;
				GlobalLocation.longitude = longitudeFromTracker;

				sourceLat = GlobalLocation.latitude;
				sourceLon = GlobalLocation.longitude;
			}
		}
		
		
		
		return false;
	}
	
	public void Remind(){
		if (Check()){
			// REMIND
		}
		
	}
	
	
	
	
	
	
}

// service of 


