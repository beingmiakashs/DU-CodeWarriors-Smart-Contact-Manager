package com.du.codewarriorcontact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.du.codewarriorcontact.adapter.DrawerListAdapter;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.settings.SettingPage;
import com.du.codewarriorcontact.social.FacebookController;
import com.du.codewarriorcontact.social.PostToFacebook;
import com.du.codewarriorcontact.taskmanager.ExportContactTask;
import com.du.codewarriorcontact.taskmanager.SyncTask;
import com.du.codewarriorcontact.util.GlobalConstant;

public class MainActivity extends FragmentActivity {

	public static FragmentActivity activity;

	private String[] drawerItems;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private int ITEM;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// init(savedInstanceState);
		// create the drawer
		drawerItems = getResources().getStringArray(R.array.drawermenulist);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		// Set the adapter for the list view
		mDrawerList.setAdapter(new DrawerListAdapter(this,
				R.layout.drawer_list_item, new ArrayList<String>(Arrays
						.asList(drawerItems))));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// getSupportActionBar().setDisplayShowHomeEnabled(true);
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		int ITEM = Utils.SELECTED_ITEM;
		if (ITEM == -1) {
			Utils.print("instance not saved");
			selectItem(0);
		} else
			selectItem(ITEM);

		// list & tab fragments.
		activity = this;
		//
		// pager = new ViewPager(this);
		// pager.setId(R.id.pager);
		// setContentView(pager);
		//
		// final ActionBar bar = getActionBar();
		// bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//
		// mTabsAdapter = new TabsAdapter(this, pager);
		// mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_launcher),
		// MessageFragment.class, null);
		// mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_launcher),
		// ContactFragment.class, null);
		// mTabsAdapter.addTab(bar.newTab().setIcon(R.drawable.ic_launcher),
		// CallHistoryFragment.class, null);
		//
		//

		GlobalConstant.mContext = getApplicationContext();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Fragment myFragment = (Fragment) getFragmentManager()
				.findFragmentByTag("HOME_PAGE");
		if (keyCode == KeyEvent.KEYCODE_BACK && myFragment != null
				&& myFragment.isVisible()) {
			// Utils.print("fragment found");
			showExitDialog();
			return false;
		}

		Fragment fragment = (Fragment) getFragmentManager().findFragmentByTag(
				"contacts");
		if (fragment != null && fragment.isVisible()) {
			if (keyCode == KeyEvent.KEYCODE_BACK && ITEM != 0) {
				selectItem(0);
				return false;
			}
		}
		// else if (keyCode == KeyEvent.KEYCODE_BACK && Utils.SELECTED_ITEM ==
		// 0) {
		//
		// showExitDialog();
		// return false;
		// }

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_sync).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			switch (item.getItemId()) {
			case R.id.action_sync:
				SyncContacts();
				break;
			case R.id.action_add:
				goToContactForm();
				break;
			case R.id.action_settings:
				Intent in = new Intent(getApplicationContext(),
						SettingPage.class);
				startActivity(in);
				break;
			case R.id.action_export_contact:
				Log.d("debug", "export contact");
				try {
					ArrayList<Contact> list = (ArrayList<Contact>) CWDAO
							.getCWdao().getContactsList(this);
					
					ExportContactTask et = new ExportContactTask(this, "Contacts-" + (new Date()).getTime() + ".csv",list);
					et.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			return true;
		} else {
			switch (item.getItemId()) {
			case R.id.action_sync:
				SyncContacts();
				break;
			case R.id.action_sync_fb:
				Intent in = new Intent(getApplicationContext(), FacebookController.class);
				startActivity(in);
				break;
			case R.id.action_add:
				goToContactForm();
				break;
			case R.id.action_settings:
				Intent in1 = new Intent(getApplicationContext(),
						SettingPage.class);
				startActivity(in1);
				break;
			case R.id.action_export_contact:
				Log.d("debug", "export contact");
				try {
					ArrayList<Contact> list = (ArrayList<Contact>) CWDAO
							.getCWdao().getContactsList(this);
					
					ExportContactTask et = new ExportContactTask(this, "Contacts.csv",list);
					et.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			return true;
		}
		// return super.onOptionsItemSelected(item);
	}

	private void goToContactForm() {
		Intent formIntent = new Intent(this, ContactsForm.class);
		startActivity(formIntent);
	}

	private void SyncContacts() {
		// FetchTask ft = new FetchTask();
		// ft
		// PhoneContactsPool pool = new PhoneContactsPool(this) ;
		//
		// pool.SyncAllContact();
		SyncTask st = new SyncTask(this, true);
		st.execute();
		Utils.print("Contacts sync finished");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// SSDAO.getSSdao().close();
		// Utils.alarmManagerm.cancel(Utils.pendingIntent);
		// unregisterReceiver(Utils.broadcastReceiver);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("selected_item", ITEM);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		ITEM = savedInstanceState.getInt("selected_item");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void showExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String builderMessage = "Do you really want to quit?";
		builder.setMessage(builderMessage)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		builder.create();
		builder.setCancelable(false);
		AlertDialog alert = builder.show();
		TextView msgView = (TextView) alert.findViewById(android.R.id.message);
	}

	private void selectItem(int position) {
		Fragment fragment = null;
		int i = 0;
		switch (position) {
		case 0:
			fragment = new Contacts();
			i = 11;
			break;
		// case 1:
		// fragment = new BazarDor();
		// break;
		// case 1:
		// fragment = new Sofol();
		// break;
		// case 2:
		// fragment = new SoncoiBriddhi();
		// break;
		// case 3:
		// fragment = new UporiAe();
		// break;
		// case 4:
		// fragment = new JiggasaFragment();
		// break;
		// case 5:
		// fragment = new SettingsFragment();
		// break;
		// default:
		// fragment = new DailyHisab();
		// break;
		}
		FragmentManager fragmentManager = getFragmentManager();
		String tag = "contacts";
		if (i == 11) {
			tag = "HOME_PAGE";
		}
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment, tag).commit();

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		// // update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
		ITEM = position;
		Utils.SELECTED_ITEM = position;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Utils.print("position clicked" + position);
			selectItem(position);
		}
	}
}