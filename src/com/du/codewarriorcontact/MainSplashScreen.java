package com.du.codewarriorcontact;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.du.codewarriorcontact.contactspool.PhoneContactsPool;

public class MainSplashScreen extends Activity {

    private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_splash_screen);
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.beep);
		mp.start();
		doSomeWork();
		
// METHOD 1		
		
         /****** Create Thread that will sleep for 5 seconds *************/  		
		Thread background = new Thread() {
			public void run() {
				
				try {
					// Thread will sleep for 5 seconds
					sleep(1000);
					// After 5 seconds redirect to another intent
				    Intent i=new Intent(getBaseContext(),MainActivity.class);
					startActivity(i);
					//Remove activity
					finish();
					
				} catch (Exception e) {
				
				}
			}
		};
		
		// start thread
		background.start();
		
//METHOD 2	
		
		/*
		new Handler().postDelayed(new Runnable() {
			 
            // Using handler with postDelayed called runnable run method
 
            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
		*/
	}
	
	private void doSomeWork() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
            public void run() {
            	Utils.poolList.add(new PhoneContactsPool());
            }
        }).start();
    }

	@Override
    protected void onDestroy() {
		
        super.onDestroy();
        
    }
}
