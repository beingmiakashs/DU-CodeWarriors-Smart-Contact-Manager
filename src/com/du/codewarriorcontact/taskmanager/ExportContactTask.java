package com.du.codewarriorcontact.taskmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.du.codewarriorcontact.Utils;
import com.du.codewarriorcontact.contactspool.IContactsPool;
import com.du.codewarriorcontact.contactspool.PhoneContactsPool;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.util.ContactExporter;
import com.du.codewarriorcontact.util.GlobalConstant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

public class ExportContactTask extends AsyncTask<Void, Void, Void> {
	ProgressDialog pd;
	Context ctx;
	String fileName;
	ArrayList<Contact> list;

	public ExportContactTask(Context ctx,String fileName, ArrayList<Contact> list) {
		this.ctx = ctx;
		this.fileName = fileName;
		this.list = list;
	}

	protected Void doInBackground(Void... params) {
		ContactExporter ce = new ContactExporter();
		String path = Environment.getExternalStorageDirectory().toString() ;
		if (!path.endsWith("/"))
			path += "/" ;
		
		
		
		path += Utils.AppFolder  ; //+ "/" + fileName;
		String dirPath = path;
		File fDir = new File(dirPath);
		if(!fDir.exists())
		{
			fDir.mkdirs();
		}
		path += "/" + fileName ;
		
		File f = new File(path);
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ce.Export(list, path);
		return null;
	}

	protected void onProgressUpdate(Integer... progress) {
		
	}

	protected void onPreExecute() {
		pd = new ProgressDialog(ctx);
		pd.setProgress(0);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.show();

	}

	protected void onPostExecute(Long result) {
		pd.hide();
		pd.dismiss();
	}
}