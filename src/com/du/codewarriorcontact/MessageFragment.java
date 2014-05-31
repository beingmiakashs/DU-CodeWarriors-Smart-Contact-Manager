package com.du.codewarriorcontact;

import java.util.ArrayList;

import com.du.codewarriorcontact.adapter.CustomAdapter;
import com.du.codewarriorcontact.adapter.CustomSMSAdapter;
import com.du.codewarriorcontact.adapter.MessageDetails;
import com.du.codewarriorcontact.dataprovider.ContactListDataProvider;
import com.du.codewarriorcontact.dataprovider.MessageHistoryDataProvider;
import com.du.codewarriorcontact.model.CallEntry;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.model.Sms;
import com.du.codewarriorcontact.util.GlobalConstant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessageFragment extends ListFragment {
	String[] list_items;
	private CustomSMSAdapter adapter;
	ArrayList<Sms> list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MessageHistoryDataProvider data = new MessageHistoryDataProvider();
		list = data.displaySMS();

		adapter = new CustomSMSAdapter(list);
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");
    	Sms sms=list.get(position);
    	String sender = sms.sender;
    	String body = sms.sms;
    	String date = sms.date;
    	
    	Intent intent = new Intent(GlobalConstant.mContext, MessageDetails.class);
    	intent.putExtra("body", body);
    	intent.putExtra("sender", sender);
    	intent.putExtra("date", date);
    	startActivity(intent);
	}
}
