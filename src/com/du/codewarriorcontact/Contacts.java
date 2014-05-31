package com.du.codewarriorcontact;

import com.du.codewarriorcontact.adapter.TabsAdapter;
import com.du.codewarriorcontact.database.CWDAO;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Contacts extends Fragment {

	private ViewPager pager;
	private TabsAdapter mTabsAdapter;
	
	SensorManager sm;
	Sensor proximity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.contacts, container, false);
		CWDAO.getCWdao().init(getActivity());
		pager = new ViewPager(getActivity());
		pager.setId(R.id.pager);
		getActivity().setContentView(pager);
		
		final ActionBar bar = getActivity().getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(MainActivity.activity, pager);
		mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_message), MessageFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_contact_white), ContactFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_call_log), CallHistoryFragment.class, null);
        
	    return view;
	}

	
}
