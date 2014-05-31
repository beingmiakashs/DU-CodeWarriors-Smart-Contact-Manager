package com.du.codewarriorcontact;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.du.codewarriorcontact.adapter.ContactDetailsAdapter;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.model.Contact;

public class ContactDetails extends ListActivity {

	private ContactDetailsAdapter adapter;
	private Contact ct = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details);
		
		String id = getIntent().getStringExtra(Utils.ContactObject);

		adapter = new ContactDetailsAdapter(this);
		setListAdapter(adapter);
		
		
		try {
			ct = CWDAO.getCWdao().getContactDetailsOfId(this, Long.parseLong(id));
			
			adapter.addBitmap(ct.photo);
			adapter.addItem("", "");
			adapter.addItem("First Name", ct.firstName);
			adapter.addItem("Last Name", ct.lastName);
			adapter.addItem("Group", ct.group);
			adapter.addItem("Mobile Number", ct.phoneNumberMobile);
			adapter.addItem("Phone Number (Home)", ct.phoneNumberHome);
			adapter.addItem("Phone Number (Work)", ct.phoneNumberWork);
			adapter.addItem("Phone Number (Other)", ct.phoneNumberOther);
			adapter.addItem("Email", ct.email);
			adapter.addItem("Address", ct.address);
			adapter.addItem("Company", ct.company);
			adapter.addItem("Job Title", ct.jobTitle);
			adapter.notifyDataSetChanged();
			
			adapter.setPhoneNo(ct);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");

	  	showInputDialog(position);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
	    	case R.id.action_save:
	    		doContactSave();
	    		break;
    	}
		return true;
        
        //return super.onOptionsItemSelected(item);
    }
    
    private void doContactSave() {
//    	int i = 1;
//    	ct.firstName = adapter.getDescriptionAtPosition(i++);
//    	ct.lastName = adapter.getDescriptionAtPosition(i++);
//    	ct.group = adapter.getDescriptionAtPosition(i++);
//    	ct.phoneNumberMobile = adapter.getDescriptionAtPosition(i++);
//    	ct.phoneNumberHome = adapter.getDescriptionAtPosition(i++);
//    	ct.phoneNumberWork = adapter.getDescriptionAtPosition(i++);
//    	ct.phoneNumberOther = adapter.getDescriptionAtPosition(i++);
//    	ct.email = adapter.getDescriptionAtPosition(i++);
//    	ct.address = adapter.getDescriptionAtPosition(i++);
//    	ct.company = adapter.getDescriptionAtPosition(i++);
//    	ct.jobTitle = adapter.getDescriptionAtPosition(i++);
//		
//		 //String photo_uri =
//		// "android.resource://com.my.package/drawable/default_photo";
//		
//		 ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//		
//		 // Name
//		 Builder builder =
//		 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//		 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?", new
//		 String[]{String.valueOf(ct.id)});
//		 builder.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
//		 ct.lastName);
//		 builder.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
//		 ct.firstName);
//		 ops.add(builder.build());
//		
//		 // Home Number
//		 builder =
//		 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//		 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?", new
//		 String[]{String.valueOf(ct.id)});
//		 builder.withValue(ContactsContract.CommonDataKinds.Phone., ct.phoneNumberHome);
//		 ops.add(builder.build());
//		 
//		 // mobile number
//		 builder =
//				 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//				 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
//				 " AND " +
//				 ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
//				 ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
//				 String[]{String.valueOf(ct.id),
//				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//				 String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)});
//				 builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ct.phoneNumberMobile);
//				 ops.add(builder.build());
//
//		 // work number
//		 builder =
//				 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//				 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
//				 " AND " +
//				 ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
//				 ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
//				 String[]{String.valueOf(ct.id),
//				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//				 String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_WORK)});
//				 builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ct.phoneNumberWork);
//				 ops.add(builder.build());
//				 
//		 // other number
//		 builder =
//				 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//				 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
//				 " AND " +
//				 ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
//				 ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
//				 String[]{String.valueOf(ct.id),
//				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//				 String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_OTHER)});
//				 builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ct.phoneNumberOther);
//				 ops.add(builder.build());
//				 
//		 builder =
//				 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//				 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
//				 " AND " +
//				 ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
//				 ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
//				 String[]{String.valueOf(ct.id),
//				 ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
//				 String.valueOf(ContactsContract.CommonDataKinds.Note.NOTE)});
//				 builder.withValue(ContactsContract.CommonDataKinds.Note.NOTE, ct.note);
//				 ops.add(builder.build());
//		
//		 builder =
//				 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//				 builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
//				 " AND " +
//				 ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
//				 ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
//				 String[]{String.valueOf(ct.id),
//				 ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
//				 String.valueOf(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)});
//				 builder.withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, ct.address);
//				 ops.add(builder.build());
//	 
//
//				 	
//		
//		 // Picture
//		 // try
//		 // {
//		 // Bitmap bitmap =
////		 MediaStore.Images.Media.getBitmap(getContentResolver(),
////		 Uri.parse(photo_uri));
////		 // ByteArrayOutputStream image = new ByteArrayOutputStream();
////		 // bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
////		 //
////		 // builder =
////		 ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
////		 // builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
////		 " AND " + ContactsContract.Data.MIMETYPE + "=?", new
////		 String[]{String.valueOf(id),
////		 ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE});
////		 // builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,
////		 image.toByteArray());
//		 // ops.add(builder.build());
//		 // }
//		 // catch (Exception e)
//		 // {
//		 // e.printStackTrace();
//		 // }
//		
//		 // Update
//		 try
//		 {
//			 
//			 this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//		 }
//		 catch (Exception e)
//		 {
//			 e.printStackTrace();
//		 }
    }
	
	private void showInputDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.contacts_input, null);
		final EditText et = (EditText) view.findViewById(R.id.input);
		if(position >=4 && position <=7){
			et.setInputType(InputType.TYPE_CLASS_PHONE);
		} else if (position == 8){
			et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		} else {
			et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
		}
		et.setHint(adapter.getTitleAtPosition(position));
		et.setText(adapter.getDescriptionAtPosition(position));
		builder.setView(view);
	    builder.setMessage(adapter.getTitleAtPosition(position))
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   adapter.setItemAtPosition(et.getText().toString(), position);
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	    builder.create();
	    builder.setCancelable(false);
	    AlertDialog alert = builder.show();
	    et.requestFocus();
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}
}
