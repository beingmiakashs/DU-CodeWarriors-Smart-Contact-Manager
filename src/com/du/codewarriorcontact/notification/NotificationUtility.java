package com.du.codewarriorcontact.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.du.codewarriorcontact.ContactDetails;
import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.Utils;
import com.du.codewarriorcontact.database.PhoneContacts;

public class NotificationUtility {

 public static void NotifyNewcontact(Context ctx, PhoneContacts contact){
  // prepare intent which is triggered if the
  // notification is selected

  Intent intent = new Intent(ctx, ContactDetails.class);
  intent.putExtra(Utils.ContactObject, contact.getRowId()) ;
  PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
  
  // build notification
  // the addAction re-use the same intent to keep the example short
  Notification.Builder n  = new Notification.Builder(ctx)
          .setContentTitle("New contact added")
          .setContentText(contact.getFirstName())
          .setSmallIcon(R.drawable.ic_launcher)
          .setContentIntent(pIntent)
          .setAutoCancel(true)
          .addAction(R.drawable.ic_launcher, "View Contact", pIntent) ;
          
      
    
  NotificationManager notificationManager = 
    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

  notificationManager.notify(0, n.build()) ;
  
 }
 public static void NotifyMultipleContacts(Context ctx, int count){
	  // prepare intent which is triggered if the
	  // notification is selected

	  
	  PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, null, 0);
	  
	  // build notification
	  // the addAction re-use the same intent to keep the example short
	  Notification.Builder n  = new Notification.Builder(ctx)
	          .setContentTitle(count + "new contacts added")
	          .setContentText(count + " new contacts added")
	          .setSmallIcon(R.drawable.ic_launcher)
	          .setContentIntent(pIntent)
	          .setAutoCancel(true)
	          .addAction(R.drawable.ic_launcher, "View Contact", pIntent) ;
	          
	      
	    
	  NotificationManager notificationManager = 
	    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

	  notificationManager.notify(0, n.build()) ;
	  
	 }

}