package com.du.codewarriorcontact.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

public abstract class MessageUtilities {
	
	public static String message;

	public static void confirmUser(Context context, String msg,
			DialogInterface.OnClickListener yesClick,
			DialogInterface.OnClickListener noClick) {
		if (context == null || msg == null || yesClick == null
				|| noClick == null)
			return;

		Builder alert = new AlertDialog.Builder(context);
		alert.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Confirmation").setMessage(msg)
				.setPositiveButton("Yes", yesClick)
				.setNegativeButton("No", noClick).show();
	}

	public static void enterMessage(Context context, String msg,
			DialogInterface.OnClickListener yesClick,
			DialogInterface.OnClickListener noClick) {
		if (context == null || msg == null || yesClick == null
				|| noClick == null)
			return;

		Builder alert = new AlertDialog.Builder(context);
		
		final EditText input = new EditText(context);  
		  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		                        LinearLayout.LayoutParams.MATCH_PARENT,
		                        LinearLayout.LayoutParams.MATCH_PARENT);
		  input.setLayoutParams(lp);
		  alert.setView(input);
		  
		  input.addTextChangedListener(new TextWatcher() {

	            @Override
	            public void onTextChanged(CharSequence s, int start, int before, int count) {
	                message = s.toString();

	            }

	            @Override
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	                // TODO Auto-generated method stub

	            }

	            @Override
	            public void afterTextChanged(Editable s) {
	                // TODO Auto-generated method stub

	            }
	        });
		
		alert.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Confirmation").setMessage(msg)
				.setPositiveButton("Yes", yesClick)
				.setNegativeButton("No", noClick).show();
	}
}
