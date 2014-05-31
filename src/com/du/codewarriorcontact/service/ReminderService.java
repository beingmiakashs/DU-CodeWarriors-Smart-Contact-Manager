package com.du.codewarriorcontact.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.database.Reminder;
import com.du.codewarriorcontact.database.WishList;
import com.du.codewarriorcontact.dataprovider.DoWish;
import com.du.codewarriorcontact.social.FacebookController;
import com.du.codewarriorcontact.social.PostToFacebook;
import com.du.codewarriorcontact.util.GlobalConstant;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class ReminderService extends Service {

	private SharedPreferences preferences;
	private Editor editor;

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

		Log.d("service", "wish firend service call");

		Intent myIntent = new Intent(getApplicationContext(),
				WishFriendService.class);
		PendingIntent pendingIntent = PendingIntent.getService(
				getApplicationContext(), 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);
		
		List<Reminder> allReminder = CWDAO.getCWdao().getAllReminder() ;
		
//		for (Reminder wl : allReminder) {
//
////			DoWish wish = new DoWish();
////			wish.msg = wl.getMessage();
////			wish.contactNumber = wl.getContactNumber();
////			wish.wishDate = wl.getDate();
//			if (wish.Check()) {
//				wish.SendWish();
//			}
//		}

	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
}