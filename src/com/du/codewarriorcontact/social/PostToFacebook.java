package com.du.codewarriorcontact.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.du.codewarriorcontact.MainActivity;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.util.GPSTracker;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.GlobalLocation;
import com.du.codewarriorcontact.util.Network;
import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.Person;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class PostToFacebook extends Activity implements LoginListener {
	/** Called when the activity is first created. */

	public FBLoginManager fbLoginManager;

	public final String FBAPP_ID = "1397110090516441";
	public User user;
	public GraphApi graphApi;
	public String type;
	public ArrayList<Person> friendList = new ArrayList();

	private double sourceLat;

	private double sourceLon;
	

	private SharedPreferences preferences;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_controller);
		
		Network netCheck = new Network(this);
		boolean networkStatus = netCheck.isNetworkConnected();

		if (networkStatus == false) {
			finish();
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		connectToFacebook();
	}

	public void connectToFacebook() {

		String permissions[] = { "user_about_me", "user_activities",
				"user_birthday", "user_checkins", "user_education_history",
				"user_events", "user_groups", "user_hometown",
				"user_interests", "user_likes", "user_location", "user_notes",
				"user_online_presence", "user_photo_video_tags", "user_photos",
				"user_relationships", "user_relationship_details",
				"user_religion_politics", "user_status", "user_videos",
				"user_website", "user_work_history", "email",

				"read_friendlists", "read_insights", "read_mailbox",
				"read_requests", "read_stream", "xmpp_login", "ads_management",
				"create_event", "manage_friendlists", "manage_notifications",
				"offline_access", "publish_checkins", "publish_stream",
				"rsvp_event", "sms",
				// "publish_actions",

				"manage_pages"

		};

		fbLoginManager = new FBLoginManager(PostToFacebook.this,
				R.layout.activity_facebook_controller, FBAPP_ID,
				permissions);

		if (fbLoginManager.existsSavedFacebook()) {
			fbLoginManager.loadFacebook();
		} else {
			fbLoginManager.login();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		Log.d("login status", "login success");
		fbLoginManager.loginSuccess(data);
	}

	public void loginSuccess(Facebook facebook) {

		graphApi = new GraphApi(facebook);
		
		GPSTracker gps = new GPSTracker(GlobalConstant.mContext);
		if (gps.canGetLocation()) {

			double latitudeFromTracker = gps.getLatitude();
			double longitudeFromTracker = gps.getLongitude();

			if (latitudeFromTracker < 1) {
				sourceLat = GlobalLocation.latitude;
				sourceLon = GlobalLocation.longitude;
			} else {
				GlobalLocation.latitude = latitudeFromTracker;
				GlobalLocation.longitude = longitudeFromTracker;

				sourceLat = GlobalLocation.latitude;
				sourceLon = GlobalLocation.longitude;
			}
		}
		
		String address="";
		try {
			address = getAddressForLocation(getApplicationContext());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		preferences = PreferenceManager.getDefaultSharedPreferences(GlobalConstant.mContext);
		String message = preferences.getString(GlobalConstant.KEY_NEAR_SERVICE_MESSAGE,"Reached safely");
		
		try {
				graphApi.setStatus(message);
		} catch (EasyFacebookError e) {
				Log.d("TAG: ", e.toString());
		}
		finish();
	}

	public void logoutSuccess() {
	}

	public void loginFail() {
	}
	
	public String getAddressForLocation(Context context) throws IOException {

		double latitude = sourceLat;
		double longitude = sourceLon;
		int maxResults = 1;

		Geocoder gc = new Geocoder(context, Locale.getDefault());
		List<Address> addresses = gc.getFromLocation(latitude, longitude,
				maxResults);

		if (addresses.size() == 1) {

			String address = addresses.get(0).getFeatureName() + "\n"
					+ addresses.get(0).getLocality();

			return address;
		} else {
			return null;
		}
	}

}
