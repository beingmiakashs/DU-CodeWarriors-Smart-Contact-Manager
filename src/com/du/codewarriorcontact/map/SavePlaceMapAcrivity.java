package com.du.codewarriorcontact.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.settings.WishFriendSetting;
import com.du.codewarriorcontact.util.GPSTracker;
import com.du.codewarriorcontact.util.GlobalConstant;
import com.du.codewarriorcontact.util.GlobalLocation;
import com.du.codewarriorcontact.util.MessageUtilities;
import com.du.codewarriorcontact.util.Network;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Address;
import android.location.Geocoder;

public class SavePlaceMapAcrivity extends FragmentActivity implements
		OnMapLongClickListener {

	private GoogleMap googleMap;
	private GPSTracker gps;
	public double sourceLat;
	public double sourceLon;
	public String address;
	public LoadMapBack loadMapBack;
	public Double queryRadious;
	public int placeType;
	public PolylineOptions rectLine = null;
	public Document document;
	public Polyline polyLine;

	public ArrayList<Marker> markerList = new ArrayList();
	private EditText searchText;
	private Network netCheck;

	public static int voiceCheck = 1639;
	
	private SharedPreferences preferences;
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		netCheck = new Network(this);
		boolean networkStatus = netCheck.isNetworkConnected();
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();

		Toast.makeText(getApplicationContext(),
				"Press long, Which place you want to save", Toast.LENGTH_LONG)
				.show();
		
		if (networkStatus == false) {
			setContentView(R.layout.no_internet);
			ImageButton img = (ImageButton) findViewById(R.id.internet_setting);
			img.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					GlobalConstant.mContext.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
				}
			});
		} else {
			setContentView(R.layout.map_activity);

			// Getting Google Play availability status
			int status = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(getBaseContext());

			// Showing status
			if (status != ConnectionResult.SUCCESS) { // Google Play Services
														// are not available
				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
						this, requestCode);
				dialog.show();

			} else { // Google Play Services are available

				// Getting reference to the SupportMapFragment of
				// activity_main.xml
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map);

				// Getting GoogleMap object from the fragment
				googleMap = fm.getMap();

				// Enabling MyLocation Layer of Google Map
				googleMap.setMyLocationEnabled(true);
				googleMap.getUiSettings().setZoomGesturesEnabled(true);
				googleMap.getUiSettings().setCompassEnabled(true);
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				googleMap.setOnMapLongClickListener(this);

				queryRadious = 100000.0;

				loadMapBack = new LoadMapBack();
				loadMapBack.execute();
			}
		}
	}

	@Override
	public void onMapLongClick(LatLng point) {
		
		editor.putString(GlobalConstant.KEY_NEAR_SERVICE_LAT, String.valueOf(point.latitude));
		editor.putString(GlobalConstant.KEY_NEAR_SERVICE_LON, String.valueOf(point.longitude));
		
		editor.commit();
		
		MessageUtilities.enterMessage(this, "Enter your message",yesStopClick, noStopClick);
		
		//setResult(RESULT_OK, null);
		//finish();
	}
	
	private DialogInterface.OnClickListener yesStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			editor.putString(GlobalConstant.KEY_NEAR_SERVICE_MESSAGE, MessageUtilities.message);
			editor.commit();
			
			Log.d("hello", MessageUtilities.message);
			
			setResult(RESULT_OK, null);
			finish();
		}
	};

	private DialogInterface.OnClickListener noStopClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
	};

	private void drawMarker(LatLng point, String title, String description) {

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(point);
		markerOptions.snippet(description);
		markerOptions.title(title);

		Marker marker = googleMap.addMarker(markerOptions);
		markerList.add(marker);
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

	public class LoadMapBack extends AsyncTask<String, String, String> {

		LoadMapBack() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			gps = new GPSTracker(SavePlaceMapAcrivity.this);
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {

			// check if GPS enabled
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
			address = "not specified";

			try {
				address = getAddressForLocation(getApplicationContext());
			} catch (IOException e) {
				address = "not specified";
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products

			runOnUiThread(new Runnable() {
				public void run() {

					LatLng point = new LatLng(sourceLat, sourceLon);

					MarkerOptions markerOptions = new MarkerOptions();
					markerOptions.position(point);
					markerOptions.snippet(address);
					markerOptions.title("My Current Location");
					markerOptions.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.user_map));

					googleMap.addMarker(markerOptions);

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(sourceLat, sourceLon)).zoom(12)
							.build();
					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));

					CircleOptions circleOptions = new CircleOptions()
							.center(point) // set center
							.radius(queryRadious) // set radius in meters
							.fillColor(0x1A0000FF) // default
							.strokeColor(Color.BLUE).strokeWidth(5);

					Circle myCircle = googleMap.addCircle(circleOptions);

				}
			});
		}

	}

}
