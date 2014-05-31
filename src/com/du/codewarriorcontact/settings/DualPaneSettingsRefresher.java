package com.du.codewarriorcontact.settings;

import android.app.Activity;

public class DualPaneSettingsRefresher {
	
	public static void refreshListAdapter(Activity activity) {
		SettingsListFragment settingsListFragment = ((SettingPage) activity).getSettingsListFragment();
		if (settingsListFragment != null) {
			settingsListFragment.refreshListAdapter();
		}
	}

}
