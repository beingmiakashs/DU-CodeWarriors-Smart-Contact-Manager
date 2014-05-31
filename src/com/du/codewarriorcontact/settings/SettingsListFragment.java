package com.du.codewarriorcontact.settings;

import java.util.ArrayList;

import com.du.codewarriorcontact.DualPaneUtils;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.util.GlobalConstant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsListFragment extends Fragment {
	ListView listSetting;

	// ArrayList<String> settingContent = new ArrayList();

	ArrayList<SettingData> settingContent;

	SettingViewAdapter adapter;

	private SharedPreferences preferences;

	private String radius;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting, container, false);
    }

	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		listSetting = (ListView) getActivity().findViewById(R.id.settingList);
		listSetting.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
	
	public void refreshListAdapter() {
		listSetting.setAdapter(getListAdapter());
	}

	@Override
	public void onStart() {
		super.onStart();

		refreshListAdapter();

		listSetting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (DualPaneUtils.isDualPane(getActivity(), R.id.settingsDetailsContainer)) {
					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					
					Fragment fragment = null;

					switch (position) {
						case 0:
							fragment = new WishFriendSettingFragment();
							break;
							
						case 1:
							fragment = new NearPlaceSettingFragment();
							break;
						
						case 2:
							fragment = new EmergencyShakeSettingFragment();
							break;
							
						case 3:
							fragment = new SynchronizeContactSettingFragment();
							break;
					}

					fragmentTransaction.replace(R.id.settingsDetailsContainer, fragment);
					fragmentTransaction.commit();
					
					return;
				}
				
				Intent in;
			
				switch(position)
				{
				
				case 0 :
				
					in = new Intent(getActivity(),WishFriendSetting.class);
					startActivity(in);
					break;
					
				case 1 :
					
					in = new Intent(getActivity(),NearPlaceSetting.class);
					startActivity(in);
					break;
				
				case 2 :
					
					in = new Intent(getActivity(),EmergencyShakeSetting.class);
					startActivity(in);
					break;
					
				case 3 :
					
					in = new Intent(getActivity(),SynchronizeContactSetting.class);
					startActivity(in);
					break;
				}
				
			}
		});	
	}

	private SettingViewAdapter getListAdapter() {
		settingContent = new ArrayList();
		
		preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

		// setting wish firends
		String wishFriendService = preferences.getString(GlobalConstant.KEY_WISH_FRIENDS_SERVICE, GlobalConstant.KEY_STATUS_OFF);	
		SettingData wishFriendServiceData = new SettingData("Wish your friend", wishFriendService);
		
		String nearMeService = preferences.getString(GlobalConstant.KEY_NEAR_ME_SERVICE, GlobalConstant.KEY_STATUS_OFF);	
		SettingData nearMeServiceData = new SettingData("Auto status update", wishFriendService);
		
		String shakeCallService = preferences.getString(GlobalConstant.KEY_SHAKE_CALL_SERVICE, GlobalConstant.KEY_STATUS_OFF);	
		SettingData shakeCallServiceData = new SettingData("Emergency call on shake", shakeCallService);
		
		String synContactService = preferences.getString(GlobalConstant.KEY_SYN_CONTACT, GlobalConstant.KEY_STATUS_OFF);	
		SettingData synContactServiceData = new SettingData("Auto synchronize contact", synContactService);

		settingContent.add(wishFriendServiceData);
		settingContent.add(nearMeServiceData);
		settingContent.add(shakeCallServiceData);
		settingContent.add(synContactServiceData);
		
		return new SettingViewAdapter(getActivity(), settingContent);
	}
}
