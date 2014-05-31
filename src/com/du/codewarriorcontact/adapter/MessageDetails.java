package com.du.codewarriorcontact.adapter;



import com.du.codewarriorcontact.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show);
	    Bundle bundle = getIntent().getExtras();
	    String body = bundle.getString("body");
	    String sender = bundle.getString("sender");
	    String date = bundle.getString("date");
	    TextView tv1 = (TextView) findViewById(R.id.textView4);
	    TextView tv2 = (TextView) findViewById(R.id.textView5);
	    TextView tv3 = (TextView) findViewById(R.id.textView6);
	    tv1.setText("Sms : "+body);
	    tv2.setText("Date : "+date);
	    tv3.setText("From : "+sender);
	    
	    

	}
}
