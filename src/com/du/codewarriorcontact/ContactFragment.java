package com.du.codewarriorcontact;

import java.sql.SQLException;
import java.util.ArrayList;

import com.du.codewarriorcontact.adapter.CustomAdapter;
import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.dataprovider.ContactListDataProvider;
import com.du.codewarriorcontact.model.Contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ContactFragment extends ListFragment{
	String[] list_items;
	private CustomAdapter adapter;
	ArrayList<Contact> list;
	ArrayList<Contact> viewList;
	View view;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
//		ContactListDataProvider data = new ContactListDataProvider();
//	      ArrayList<Contact> list = data.displayContacts();
		EditText inputSearch = null;
		if(view == null) {
			view = inflater.inflate(R.layout.contacts_list, container, false);
			inputSearch = (EditText) view.findViewById(R.id.inputSearch);
			inputSearch.addTextChangedListener(new TextWatcher() {
			     
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			       filter(cs);   
			    }
			     
			    @Override
			    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			            int arg3) {
			         
			    }
			     
			    @Override
			    public void afterTextChanged(Editable arg0) {
			    	
			    }
			});
		}
		viewList = new ArrayList<Contact>();
		try {
			list = (ArrayList<Contact>) CWDAO.getCWdao().getContactsList(getActivity());
			viewList.addAll(list);
			adapter = new CustomAdapter(viewList) ;
		    setListAdapter(adapter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Utils.print("Cant fetch Contact Data");
		}
		
//	      return super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	  	Utils.print("clicked");

	  	Contact ct = (Contact) adapter.getItem(position);
	  	Intent detailsIntent = new Intent(getActivity(), ContactDetails.class);
	  	detailsIntent.putExtra(Utils.ContactObject, ct.id);
	  	getActivity().startActivity(detailsIntent);
	}
	
	private void filter(CharSequence cs) {
//		ArrayList<Contact> newList = new ArrayList<Contact>();
		viewList.clear();
		for(Contact c : list) {
			boolean findFlag = false;
			if(c.firstName!=null)
			{
				if(c.firstName.contains(cs) ){
					findFlag = true;
				}
			}
			
			if(c.phoneNumberMobile!=null)
			{
				if(c.phoneNumberMobile.contains(cs) ){
					findFlag = true;
				}
			}
			
			if(c.email!=null)
			{
				if(c.email.contains(cs) ){
					findFlag = true;
				}
			}
			
			if(findFlag) viewList.add(c);
		}
		if(viewList!=null)adapter.notifyDataSetChanged();
//		if(newList.size() > 0) {
//			list.clear();
//			list.addAll(newList);
//			Log.e("contact array size", (Integer.toString(list.size())));
//			adapter.notifyDataSetChanged();
//		}
	}
}
