package com.du.codewarriorcontact.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.database.FacebookContacts;
import com.du.codewarriorcontact.database.SingleContacts;
import com.easy.facebook.android.apicall.FQL;
import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.Person;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class FacebookController extends Activity implements LoginListener {
	/** Called when the activity is first created. */

	public FBLoginManager fbLoginManager;

	// replace it with your own Facebook App ID
	public final String FBAPP_ID = "1397110090516441";
	public User user;
	public GraphApi graphApi;
	public String type;
	public List<Person> friendList = new ArrayList();
	ArrayList<FacebookContacts> fbContactList = new ArrayList<FacebookContacts>();

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_controller);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		connectToFacebook();
	}

	public void connectToFacebook() {

		// read about Facebook Permissions here:
		// http://developers.facebook.com/docs/reference/api/permissions/
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

		fbLoginManager = new FBLoginManager(FacebookController.this,
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
		FQL fql = new FQL(facebook);
		
		try {
			friendList = graphApi.getFriends(10);
			for(Person friend : friendList)
			{
				Log.d("firend", friend.getId()+" "+friend.getName());
				User friendInfo = graphApi.getUserInfo(friend.getId());
				
				
				FacebookContacts fbContact = new FacebookContacts(friend.getId(),friendInfo.getFirst_name()
						,friendInfo.getLast_name(),friendInfo.getEmail(),friendInfo.getEmail(),
						"");
				
				CWDAO.getCWdao().getFacebookContactsDAO().createOrUpdate(fbContact);
				
				CWDAO.getCWdao().getSingleContactsDAO().createOrUpdate(new SingleContacts("","",friend.getId()));
				
				fbContactList.add(fbContact);
			}
		} catch (EasyFacebookError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finish();

	}

	public void logoutSuccess() {
		fbLoginManager.displayToast("Logout Success!");
	}

	public void loginFail() {
		fbLoginManager.displayToast("Login Epic Failed!");
	}

}
