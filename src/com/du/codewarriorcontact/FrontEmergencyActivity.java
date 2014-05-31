package com.du.codewarriorcontact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.du.codewarriorcontact.database.CWDAO;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class FrontEmergencyActivity extends Activity {
	
	private SharedPreferences preferences;
	private LocationManager locationManager;
	private TelephonyManager telephonyManager;
	private PhoneStateListener listener;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String status = preferences.getString("sms", "0");
		
		Log.d("shake", "call");
		
		String phoneNumber="";
		try {
			phoneNumber = CWDAO.getCWdao().hasEmergency();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(phoneNumber.length()>1)makeCall(phoneNumber);
		
		
	}
	
	public void makeCall(String number) {
		String phoneCallUri = "tel:";
		phoneCallUri += number;

		// Get the telephony manager
		telephonyManager = (TelephonyManager) MainActivity.activity
				.getSystemService(Context.TELEPHONY_SERVICE);

		listener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				String stateString = "N/A";
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					stateString = "Idle";
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					stateString = "Off Hook";
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					stateString = "Ringing";
					break;
				}
			}
		};

		// Register the listener wit the telephony manager
		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
		phoneCallIntent.setData(Uri.parse(phoneCallUri));
		MainActivity.activity.startActivity(phoneCallIntent);

	}

	
}
