package com.du.codewarriorcontact;

import com.du.codewarriorcontact.util.GlobalConstant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
 
public class StartAtBootServiceReceiver extends BroadcastReceiver 
{
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			preferences = PreferenceManager.getDefaultSharedPreferences(context);
			editor = preferences.edit();
			
			Log.d("on boot","called");
		}
	}
}