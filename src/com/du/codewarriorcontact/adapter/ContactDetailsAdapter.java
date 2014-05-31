package com.du.codewarriorcontact.adapter;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.database.Reminder;
import com.du.codewarriorcontact.database.WishList;
import com.du.codewarriorcontact.model.Contact;

public class ContactDetailsAdapter  extends BaseAdapter{
	private ArrayList<String> titleList = new ArrayList<String>() ;
	private ArrayList<String> descList = new ArrayList<String>() ;
	Bitmap bp = null;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	Activity activity;
	String phoneNo = "";
	
	public ContactDetailsAdapter(Activity ac){
//		mInflater = (LayoutInflater)ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = ac;
	}

	public void addItem(String title, String desc) {
		titleList.add(title);
		descList.add(desc);
//		notifyDataSetChanged();
	}
	
	public void addBitmap(Bitmap bp) {
		this.bp = bp;
	}
	
	public void setItemAtPosition(String desc, int position) {
		descList.set(position, desc);
		notifyDataSetChanged();
	}
	
	public String getDescriptionAtPosition(int position) {
		return descList.get(position);
	}
	
	public String getTitleAtPosition(int position) {
		return titleList.get(position);
	}
	
	public void setPhoneNo(Contact ct) {
		if (ct.phoneNumberOther.length() > 0){
			phoneNo = ct.phoneNumberOther;
		} else if(ct.phoneNumberHome.length() > 0){
			phoneNo = ct.phoneNumberHome;
		} else if(ct.phoneNumberWork.length() > 0){
			phoneNo = ct.phoneNumberWork;
		} else if(ct.phoneNumberMobile.length() > 0){
			phoneNo = ct.phoneNumberMobile;
		}
	}
	
	@Override
    public int getItemViewType(int position) {
        return (position == 0) ? 0 : 1;
    }

	@Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public String getItem(int position) {
        return titleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
	public static class ViewHolder{
		public TextView textTitle ;
		public TextView textDescription ;
		public ImageView photo;
	}
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		holder = null;
//		if (view == null){
			holder = new ViewHolder();
			LayoutInflater li = LayoutInflater.from(parent.getContext()) ;
			if(index == 0) {
//				view = mInflater.inflate(R.layout.contact_details_photo, null);
				view = li.inflate(R.layout.contact_details_photo, parent, false) ;
				holder.photo = (ImageView) view.findViewById(R.id.contactPhoto);
				
				addListenerOnWishListChk(view);
				addListenerOnReminderChk(view);
				addListenerOnEmergencyChk(view);
			} else {
//				view = mInflater.inflate(R.layout.contact_details_text, null);
				view = li.inflate(R.layout.contact_details_text, parent, false) ;
				holder.textTitle = (TextView) view.findViewById(R.id.titleText);
				holder.textDescription = (TextView) view.findViewById(R.id.descText);
			}
//			view.setTag(holder) ;
//		}else
//			holder = (ViewHolder)view.getTag() ;
		
		if(index == 0){
			holder.photo.setImageBitmap(this.bp);
		} else {
			holder.textTitle.setText(titleList.get(index));
			holder.textDescription.setText(descList.get(index));
		}
		
		
		return view;
	}

	private void addListenerOnWishListChk(View v) {
		final CheckBox wishChk = (CheckBox) v.findViewById(R.id.chkWishList);
		wishChk.setOnClickListener(new OnClickListener() {
			 
		  @Override
		  public void onClick(View v) {
	                //is chkIos checked?
			if (((CheckBox) v).isChecked()) {
				showReminderWishDialog(wishChk, true);
			}
	 
		  }
		});
	}
	
	private void addListenerOnReminderChk(View v) {
		final CheckBox wishChk = (CheckBox) v.findViewById(R.id.chkReminder);
		wishChk.setOnClickListener(new OnClickListener() {
			 
		  @Override
		  public void onClick(View v) {
	                //is chkIos checked?
			if (((CheckBox) v).isChecked()) {
				showReminderWishDialog(wishChk, false);
			}
	 
		  }
		});
	}
	private CheckBox wishChk;
	public void addListenerOnEmergencyChk(View v) {
		wishChk = (CheckBox) v.findViewById(R.id.chkEmergency);
		wishChk.setOnClickListener(new OnClickListener() {
			 
		  @Override
		  public void onClick(View v) {
	                //is chkIos checked?
			  Log.d("emergency check", "call"+" "+phoneNo);
			if (((CheckBox) v).isChecked()) {
				try {
					Log.d("emergency check", "call"+" "+phoneNo);
					CWDAO.getCWdao().updateIsEmergency(phoneNo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	 
		  }
		});
	}
	
	private void showReminderWishDialog(final CheckBox wishChk, final boolean b) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.reminder_wishlist, null);
		final EditText dateInput = (EditText) view.findViewById(R.id.date_input);
		dateInput.setHint("Set Date");
		final EditText msg = (EditText) view.findViewById(R.id.msg);
		msg.setHint("Message");
		builder.setView(view);
		String builderMessage = "";
	    builder.setMessage(builderMessage)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   if(b == true){
	            		   updateDBForWishList(dateInput.toString(), msg.toString());
	            	   } else {
	            		   updateDBForReminder(dateInput.toString(), msg.toString());
	            	   }
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   wishChk.setChecked(false);
	               }
	           });
	    builder.create();
	    builder.setCancelable(false);
	    AlertDialog alert = builder.show();
	}

	private void updateDBForWishList(String dateTime, String msg) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = null;
		try {
			dt = format.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		CWDAO.getCWdao().getWishListDAO().createOrUpdate(new WishList(msg, phoneNo, dt));
	}
	
	private void updateDBForReminder(String dateTime, String msg) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = null;
		try {
			dt = format.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		CWDAO.getCWdao().getReminderDAO().createOrUpdate(new Reminder(phoneNo, dt, msg));
	}
}
