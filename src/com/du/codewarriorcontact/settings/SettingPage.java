package com.du.codewarriorcontact.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.settings.SettingsListFragment;

public class SettingPage extends FragmentActivity {
	
	SettingsListFragment settingsListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_list);
		
		settingsListFragment = (SettingsListFragment) getSupportFragmentManager().findFragmentById(R.id.settingsListFragment);
	}
	
	public SettingsListFragment getSettingsListFragment() {
		return settingsListFragment;
	}
}
