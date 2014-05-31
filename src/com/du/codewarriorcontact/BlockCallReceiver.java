package com.du.codewarriorcontact;

import java.lang.reflect.Method;
import java.util.Locale;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

public class BlockCallReceiver extends BroadcastReceiver implements
		SensorEventListener {

	public static String phoneNumber;

	private SmsManager smsManager;

	SharedPreferences.Editor editor;
	private SharedPreferences preferences;

	SensorManager sm;
	Sensor proximity;
	
	public static Context context;

	@Override
	public void onReceive(Context mContext, Intent intent) {

		sm = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
		proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sm.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);

		/*
		 * preferences =
		 * PreferenceManager.getDefaultSharedPreferences(mContext); editor =
		 * preferences.edit();
		 * 
		 * String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		 * 
		 * if (state == null) return;
		 * 
		 * if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		 * editor.putString("isRing", "yes"); editor.putString("isCallReceived",
		 * "no");
		 * 
		 * Bundle bundle = intent.getExtras(); phoneNumber =
		 * bundle.getString("incoming_number"); editor.putString("phoneNumber",
		 * intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
		 * 
		 * editor.commit(); }
		 * 
		 * else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
		 * editor.putString("isCallReceived", "yes"); editor.commit(); }
		 * 
		 * else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
		 * 
		 * if (preferences.getString("isRing", "no").equals("yes") &&
		 * preferences.getString("isCallReceived", "no").equals("no")) {
		 * 
		 * }
		 * 
		 * editor.putString("isRing", "no"); editor.putString("isCallReceived",
		 * "no"); }
		 */

		// Bundle myBundle = intent.getExtras();
		// if (myBundle != null) {
		// System.out.println("--------Not null-----");
		// try {
		// if (intent.getAction().equals(
		// "android.intent.action.PHONE_STATE")) {
		// String state = intent
		// .getStringExtra(TelephonyManager.EXTRA_STATE);
		// System.out.println("--------in state-----");
		// if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
		// // Incoming call
		// String incomingNumber = intent
		// .getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		// System.out.println("--------------my number---------"
		// + incomingNumber);
		//
		// // this is main section of the code,. could also be use
		// // for particular number.
		// // Get the boring old TelephonyManager.
		// TelephonyManager telephonyManager = (TelephonyManager) mContext
		// .getSystemService(Context.TELEPHONY_SERVICE);
		//
		// // Get the getITelephony() method
		// Class<?> classTelephony =
		// Class.forName(telephonyManager.getClass().getName());
		// Method methodGetITelephony = classTelephony
		// .getDeclaredMethod("getITelephony");
		//
		// // Ignore that the method is supposed to be private
		// methodGetITelephony.setAccessible(true);
		//
		// // Invoke getITelephony() to get the ITelephony
		// // interface
		// Object telephonyInterface = methodGetITelephony
		// .invoke(telephonyManager);
		//
		// // Get the endCall method from ITelephony
		// Class<?> telephonyInterfaceClass = Class
		// .forName(telephonyInterface.getClass()
		// .getName());
		// Method methodEndCall = telephonyInterfaceClass
		// .getDeclaredMethod("endCall");
		//
		// // Invoke endCall()
		// methodEndCall.invoke(telephonyInterface);
		//
		// }
		//
		// }
		// } catch (Exception ex) { // Many things can go wrong with reflection
		// // calls
		// ex.printStackTrace();
		// }
		// }

		
		String extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		if (extraState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

			String incomingNumber = intent
					.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			if (incomingNumber.contentEquals("84009085")) {

				
			}
		}

	}

	void showNotification(Context context, String phnNumber) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Auto SMS on Call")
				.setContentText("An Auto SMS is send to " + phnNumber);
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

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent eventSensor) {
		// TODO Auto-generated method stub
		Log.d("Akash", eventSensor.values[0] + "");
		if (eventSensor.values[0] == 1 && context!=null) {
			Log.d("Akash", "hello sensor");
			Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
			KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN,
					KeyEvent.KEYCODE_HEADSETHOOK);
			i.putExtra(Intent.EXTRA_KEY_EVENT, event);
			context.sendOrderedBroadcast(i, null);
		}

	}

}
