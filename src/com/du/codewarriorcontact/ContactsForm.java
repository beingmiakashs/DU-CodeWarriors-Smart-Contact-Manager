package com.du.codewarriorcontact;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.du.codewarriorcontact.adapter.ContactDetailsAdapter;
import com.du.codewarriorcontact.contactspool.PhoneContactsPool;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.model.Contact;

public class ContactsForm extends ListActivity {

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
				ct = new Contact();
				
				adapter.addBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image_placeholder));
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
	    	int i = 1;
	    	ct.firstName = adapter.getDescriptionAtPosition(i++);
	    	ct.lastName = adapter.getDescriptionAtPosition(i++);
	    	ct.group = adapter.getDescriptionAtPosition(i++);
	    	ct.phoneNumberMobile = adapter.getDescriptionAtPosition(i++);
	    	ct.phoneNumberHome = adapter.getDescriptionAtPosition(i++);
	    	ct.phoneNumberWork = adapter.getDescriptionAtPosition(i++);
	    	ct.phoneNumberOther = adapter.getDescriptionAtPosition(i++);
	    	ct.email = adapter.getDescriptionAtPosition(i++);
	    	ct.address = adapter.getDescriptionAtPosition(i++);
	    	ct.company = adapter.getDescriptionAtPosition(i++);
	    	ct.jobTitle = adapter.getDescriptionAtPosition(i++);
	    	
	    	PhoneContactsPool pcp = PhoneContactsPool.getInstance();
	    	pcp.CreateContact(this, ct);
	    	finish();
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
		}
	}
