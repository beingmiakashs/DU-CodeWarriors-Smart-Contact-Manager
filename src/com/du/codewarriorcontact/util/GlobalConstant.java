package com.du.codewarriorcontact.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class GlobalConstant {
	
	public static Context mContext;

	public static final String KEY_STATUS_ON = "on";
	public static final String KEY_STATUS_OFF = "off";
	
	public static final String KEY_ON = "ON";
	public static final String KEY_OFF = "OFF";
	
	public static final String KEY_WISH_FRIENDS_SERVICE = "KEY_WISH_FRIENDS_SERVICE";
	public static final String KEY_NEAR_ME_SERVICE = "KEY_NEAR_ME_SERVICE";
	public static final String KEY_SHAKE_CALL_SERVICE = "shake";
	public static final String KEY_NEAR_SERVICE_LAT = "KEY_NEAR_SERVICE_LAT";
	public static final String KEY_NEAR_SERVICE_LON = "KEY_NEAR_SERVICE_LON";
	public static final String KEY_SYN_CONTACT = "KEY_SYN_CONTACT";
	public static final String KEY_NEAR_SERVICE_MESSAGE = "KEY_NEAR_SERVICE_MESSAGE";
	//public static final String KEY_
	
	public static final String KEY_ACTION_TYPE = "action_type";
	public static final String KEY_ACTION_NEAR = "action_near";
	
	public static Typeface banglaTypeFace = null;
	public static Typeface banglaTypeFaceSutonny = null;
	
//	public static void setActionBarTitle(Activity ac, String ss){
//		SpannableString s = new SpannableString(ss);
//		s.setSpan(new TypefaceSpan(ac, GlobalConstant.banglaTypeFace, "banglaTypeFace"), 0, s.length(),
//		        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		ac.getActionBar().setTitle(s);
//	}
	

	public static double distanceBetweenTwoPoint(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		double meterConversion = 1609;

		return (int) (dist * meterConversion);
	}
}
