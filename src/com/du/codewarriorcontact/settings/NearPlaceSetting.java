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
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.MessageUtilities;

public class NearPlaceSetting extends Activity {
	
	private CheckBox nearPlaceCheck;
	private SharedPreferences preferences;
	private Editor editor;
	
	private Intent myIntent;
	private PendingIntent pendingIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_near_place);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		nearPlaceCheck = (CheckBox) findViewById(R.id.near_place_check);
		
		String status = preferences.getString(GlobalConstant.KEY_NEAR_ME_SERVICE, GlobalConstant.KEY_OFF);
		
		if(status.equals(GlobalConstant.KEY_ON))
			nearPlaceCheck.setChecked(true);
		else
			nearPlaceCheck.setChecked(false);
		
		
		nearPlaceCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (nearPlaceCheck.isChecked())
				{
					MessageUtilities.confirmUser(NearPlaceSetting.this, "Do you really want to start near place status update ?",yesStopClick, noStopClick);
				}
				else
				{
					editor.putString(GlobalConstant.KEY_NEAR_ME_SERVICE, GlobalConstant.KEY_OFF);
					editor.commit();
					Toast.makeText(getApplicationContext(), "Near place status update is disabled now", Toast.LENGTH_SHORT).show();
					turnServiceOff();
				}
			}
		});
	}
	
	private DialogInterface.OnClickListener yesStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			Intent in = new Intent(getApplicationContext(),SavePlaceMapAcrivity.class);
			startActivityForResult(in, 1);
		}
	};

	private DialogInterface.OnClickListener noStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			nearPlaceCheck.setChecked(false);
		}
	};
	
	private void turnServiceON()
	{
		myIntent = new Intent(getApplicationContext(),NearMeService.class);
		pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
	}
	
	private void turnServiceOff()
	{
		myIntent = new Intent(getApplicationContext(),NearMeService.class);
		pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			
			Log.d("get result", "call");
			
			editor.putString(GlobalConstant.KEY_NEAR_ME_SERVICE, GlobalConstant.KEY_ON);
			editor.commit();
			Toast.makeText(getApplicationContext(), "Near place status update is active now", Toast.LENGTH_SHORT).show();
			
			turnServiceON();
		}
		else{
			nearPlaceCheck.setChecked(false);
		}
	}

}