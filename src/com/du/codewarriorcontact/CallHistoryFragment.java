package com.du.codewarriorcontact;

import java.util.ArrayList;

import com.du.codewarriorcontact.adapter.CustomAdapter;
import com.du.codewarriorcontact.adapter.CustomCallAdapter;
import com.du.codewarriorcontact.dataprovider.CallHistoryDataProvider;
import com.du.codewarriorcontact.dataprovider.ContactListDataProvider;
import com.du.codewarriorcontact.model.CallEntry;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.util.GlobalConstant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CallHistoryFragment extends ListFragment {
	String[] list_items;
	private CustomCallAdapter adapter;
	private TelephonyManager telephonyManager;
	private PhoneStateListener listener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		CallHistoryDataProvider data = new CallHistoryDataProvider();
		ArrayList<CallEntry> list = data.displayCallLog();

		adapter = new CustomCallAdapter(list);
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");

	  	CallEntry ct = (CallEntry) adapter.getItem(position);
	  	if(ct.phNumber!=null)makeCall(ct.phNumber);
	}
	
	public void makeCall(String number) {
		String phoneCallUri = "tel:";
		phoneCallUri += number;
		
		telephonyManager = (TelephonyManager) GlobalConstant.mContext
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

		telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
		phoneCallIntent.setData(Uri.parse(phoneCallUri));
		phoneCallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		GlobalConstant.mContext.startActivity(phoneCallIntent);

	}
}
