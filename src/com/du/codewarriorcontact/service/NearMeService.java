package com.du.codewarriorcontact.service;

import java.util.Calendar;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.social.FacebookController;
import com.du.codewarriorcontact.social.PostToFacebook;
import com.du.codewarriorcontact.util.GPSTracker;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.GlobalLocation;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings.Global;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NearMeService extends Service {

	private SharedPreferences preferences;
	private Editor editor;
	private double sourceLat;
	private double sourceLon;

	@Override
	public void onCreate() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		preferences = PreferenceManager
				.getDefaultSharedPreferences(GlobalConstant.mContext);
		editor = preferences.edit();

		Log.d("service", "near service call");

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
		double storeLat = Double.valueOf(preferences.getString(GlobalConstant.KEY_NEAR_SERVICE_LAT, "0"));
		double storeLon = Double.valueOf(preferences.getString(GlobalConstant.KEY_NEAR_SERVICE_LON, "0"));
		double distance = GlobalConstant.distanceBetweenTwoPoint(sourceLat, sourceLon, storeLat, storeLon);

		if(distance<500000000)
		{
			showNotification(GlobalConstant.mContext,"You reached your destination check your status update");
			
			Intent in = new Intent(getApplicationContext(), PostToFacebook.class);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(in);
	
			editor.putString(GlobalConstant.KEY_NEAR_ME_SERVICE,
					GlobalConstant.KEY_OFF);
			editor.commit();
		}
		else{
			Intent myIntent = new Intent(getApplicationContext(),
					NearMeService.class);
			PendingIntent pendingIntent = PendingIntent.getService(
					getApplicationContext(), 0, myIntent, 0);
	
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 5);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pendingIntent);
		}

	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	void showNotification(Context context, String message) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Auto status update")
				.setContentText(message);
		Intent resultIntent = new Intent(context, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
	}
}