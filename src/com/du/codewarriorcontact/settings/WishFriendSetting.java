package com.du.codewarriorcontact.settings;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.map.SavePlaceMapAcrivity;
import com.du.codewarriorcontact.service.NearMeService;
import com.du.codewarriorcontact.service.WishFriendService;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.MessageUtilities;

public class WishFriendSetting extends Activity {
	
	private CheckBox wishFriendCheck;
	private SharedPreferences preferences;
	private Editor editor;
	
	private Intent myIntent;
	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_wish_friend);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		wishFriendCheck = (CheckBox) findViewById(R.id.wish_friend_check);
		
		String status = preferences.getString(GlobalConstant.KEY_WISH_FRIENDS_SERVICE, GlobalConstant.KEY_OFF);
		
		if(status.equals(GlobalConstant.KEY_ON))
			wishFriendCheck.setChecked(true);
		else
			wishFriendCheck.setChecked(false);
		
		
		wishFriendCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (wishFriendCheck.isChecked())
				{
					MessageUtilities.confirmUser(WishFriendSetting.this, "Do you really want to start auto wish your friend from contact list ?",yesStopClick, noStopClick);
				}
				else
				{
					editor.putString(GlobalConstant.KEY_WISH_FRIENDS_SERVICE, GlobalConstant.KEY_OFF);
					editor.commit();
					Toast.makeText(getApplicationContext(),  "Friend wisher is disabled", Toast.LENGTH_SHORT).show();
					turnServiceOff();
				}
			}
		});
	}
	
	private DialogInterface.OnClickListener yesStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			editor.putString(GlobalConstant.KEY_WISH_FRIENDS_SERVICE, GlobalConstant.KEY_ON);
			editor.commit();
			Toast.makeText(getApplicationContext(),  "Friend wisher is ebabled", Toast.LENGTH_SHORT).show();
			
			turnServiceON();
		}
	};

	private DialogInterface.OnClickListener noStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			wishFriendCheck.setChecked(false);
		}
	};
	
	private void turnServiceON()
	{
		myIntent = new Intent(getApplicationContext(),WishFriendService.class);
		pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
	}
	
	private void turnServiceOff()
	{
		myIntent = new Intent(getApplicationContext(),WishFriendService.class);
		pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

}