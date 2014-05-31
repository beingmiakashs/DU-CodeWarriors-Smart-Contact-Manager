package com.du.codewarriorcontact.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.du.codewarriorcontact.R;
import com.du.codewarriorcontact.model.CallEntry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCallAdapter extends BaseAdapter{
	private ArrayList<CallEntry> list = new ArrayList<CallEntry>() ;

	public void add(CallEntry t){
		list.add(t) ;
	}
	public CustomCallAdapter(ArrayList<CallEntry> listRecord){
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
		public  TextView type ;
		
		
		
	}
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		ViewHolder holder ;
		if (view == null){
			LayoutInflater li = LayoutInflater.from(parent.getContext()) ;
			view = li.inflate(R.layout.list_item_call_entry, parent, false) ;
			holder = new ViewHolder() ;
			holder.txtTime = (TextView)view.findViewById(R.id.textView1) ;
			holder.txtNote = (TextView)view.findViewById(R.id.textView2) ;
			holder.time = (TextView)view.findViewById(R.id.textView3) ;
			holder.type = (TextView)view.findViewById(R.id.textView4) ;

			view.setTag(holder) ;
		}else
			holder = (ViewHolder)view.getTag() ;

		CallEntry item = list.get(index) ;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		String strDate = sdf.format(c.getTime());
		
		int duration = Integer.parseInt(item.callDuration);
		int min = duration/60;
		int sec = duration%60;
		String s = min+" Min "+sec+" Sec";
		

		holder.txtTime.setText(item.phNumber) ;
		holder.txtNote.setText(item.callDayTime.toString()) ;
		holder.time.setText(s) ;
		holder.type.setText(item.calltype);
		
		

		return view;
	}

}
