package com.du.codewarriorcontact.taskmanager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.du.codewarriorcontact.Utils;
import com.du.codewarriorcontact.contactspool.IContactsPool;

public class SyncTask extends AsyncTask<Void, Void, Void> {
	ProgressDialog pd ;
	Context ctx ;
	boolean showPD;
	public SyncTask(Context ctx, boolean showPD){
		this.ctx = ctx ;
		this.showPD = showPD;
	}
	
     protected Void doInBackground(Void ... params) {
    	 for(IContactsPool pool : Utils.poolList)
    		 pool.SyncAllContact(ctx);
    	 return null ;
     }

     protected void onProgressUpdate(Integer... progress) {
    	 
         
     }
     protected void onPreExecute(){
    	 if(showPD)
    	 {
	    	 pd = new ProgressDialog(ctx) ;
	    	 pd.setProgress(0);
	    	 pd.setMessage("Syncing Contacts .. Please Wait ...");
	    	 pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    	 pd.show();
    	 }
     }
     
     protected void onPostExecute(Long result) {
    	 if(showPD)
    	 {
	         pd.hide();
	         pd.dismiss();
    	 }
    }
 }