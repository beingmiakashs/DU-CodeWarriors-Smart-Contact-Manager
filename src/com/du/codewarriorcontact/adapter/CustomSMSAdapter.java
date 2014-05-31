package com.du.codewarriorcontact.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.model.Sms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSMSAdapter extends BaseAdapter{
	private ArrayList<Sms> list = new ArrayList<Sms>() ;

	public void add(Sms t){
		list.add(t) ;
	}
	public CustomSMSAdapter(ArrayList<Sms> listRecord){
		this.list = listRecord ;

	}
	@Override
	public int getCount() {
		return list.size() ;
	}

	@Override
	public Object getItem(int index) {
		return (Object)list.get(index) ;
	}

	@Override
	public long getItemId(int index) {
		return index ;
	}

	public static class ViewHolder{
		public TextView txtNote ;
		public TextView txtTime ;
		public TextView time ;
		public  ImageView imageView1 ;
	}
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		ViewHolder holder ;
		if (view == null){
			LayoutInflater li = LayoutInflater.from(parent.getContext()) ;
			view = li.inflate(R.layout.list_item, parent, false) ;
			holder = new ViewHolder() ;
			holder.txtTime = (TextView)view.findViewById(R.id.textView1) ;
			holder.txtNote = (TextView)view.findViewById(R.id.textView2) ;
			holder.time = (TextView)view.findViewById(R.id.textView3) ;

			view.setTag(holder) ;
		}else
			holder = (ViewHolder)view.getTag() ;

		Sms item = list.get(index) ;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String strDate = sdf.format(c.getTime());

		holder.txtTime.setText(item.sender) ;
		holder.txtNote.setText(item.sms) ;
		holder.time.setText(item.date) ;

		return view;
	}

}
