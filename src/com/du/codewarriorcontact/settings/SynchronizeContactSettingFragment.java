package com.du.codewarriorcontact.settings;

import java.util.Calendar;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.service.SynchronizeContactService;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.MessageUtilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;


public class SynchronizeContactSettingFragment extends Fragment {
	
	private CheckBox synContactCheck;
	private SharedPreferences preferences;
	private Editor editor;
	
	private Intent myIntent;
	private PendingIntent pendingIntent;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_syn_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	
    	preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		editor = preferences.edit();
		
		
		synContactCheck = (CheckBox) getActivity().findViewById(R.id.syn_contact_check);
		
		
		String status = preferences.getString(GlobalConstant.KEY_SYN_CONTACT, GlobalConstant.KEY_OFF);
		
		if(status.equals(GlobalConstant.KEY_ON))
			synContactCheck.setChecked(true);
		else
			synContactCheck.setChecked(false);
		
		
		synContactCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (synContactCheck.isChecked())
				{
					MessageUtilities.confirmUser(getActivity(), "Do you really want to start auto synchronize contact ?",yesStopClick, noStopClick);
				}
				else
				{
					editor.putString(GlobalConstant.KEY_SYN_CONTACT, GlobalConstant.KEY_OFF);
					editor.commit();
					Toast.makeText(getActivity(), "Synchronization of contact is disabled now", Toast.LENGTH_SHORT).show();
					turnServiceOff();
				}
				DualPaneSettingsRefresher.refreshListAdapter(getActivity());
			}
		});

    }
    
    private DialogInterface.OnClickListener yesStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			editor.putString(GlobalConstant.KEY_SYN_CONTACT, GlobalConstant.KEY_ON);
			editor.commit();
			Toast.makeText(getActivity(), "Synchronization of contact is enabled now", Toast.LENGTH_SHORT).show();
			
			turnServiceON();
		}
	};

	private DialogInterface.OnClickListener noStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			synContactCheck.setChecked(false);
		}
	};
	
	private void turnServiceON()
	{
		myIntent = new Intent(getActivity(),SynchronizeContactService.class);
		pendingIntent = PendingIntent.getService(getActivity(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 5);
		alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
	}
	
	private void turnServiceOff()
	{
		myIntent = new Intent(getActivity(),SynchronizeContactService.class);
		pendingIntent = PendingIntent.getService(getActivity(), 0, myIntent,0);
		
		AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

}
