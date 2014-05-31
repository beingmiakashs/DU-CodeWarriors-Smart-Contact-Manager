package com.du.codewarriorcontact.settings;

import java.util.ArrayList;
import java.util.HashMap;

import com.du.codewarriorcontact.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingViewAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<SettingData> data;
    private static LayoutInflater inflater=null;
    private String type;
    
    public SettingViewAdapter(Activity a, ArrayList<SettingData> data) {
        activity = a;
        this.data=data;
        Log.d("list items", data.toString());
        Log.d("list Service", ""+data.size());
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        
    	Log.d("list pos", ""+position);
    	
    	return position;
    }

    @Override
    public long getItemId(int position) {
    	
    	Log.d("list Id", ""+position);
    	
        return position;
    }
    
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	View vi=convertView;
    	//if the view not created it has to be... if not doesn't need to recreate .. just return
    	
       // if(convertView==null)
        {   
        	
        vi = inflater.inflate(R.layout.setting_row, null);
        
        TextView titleSetting = (TextView)vi.findViewById(R.id.title_setting); // title
        TextView overView = (TextView) vi.findViewById(R.id.overView);
        
        SettingData currentData = data.get(position);
        
        titleSetting.setText(currentData.getTitle());
        overView.setText(currentData.getOverView());
        
        
        }
        return vi;
    }
}