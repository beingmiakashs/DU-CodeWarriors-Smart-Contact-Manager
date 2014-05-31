package com.du.codewarriorcontact.database;

import android.content.Context;

import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "ContactManagerCWC.db";
	private static final int DATABASE_VERSION = 2;

	private RuntimeExceptionDao<FacebookContacts, Integer> facebookContactsDAO = null;
	private RuntimeExceptionDao<GmailContacts, Integer> gmailContactsDAO = null;
	private RuntimeExceptionDao<PhoneContacts, Integer> phoneContactsDAO = null;
	private RuntimeExceptionDao<SingleContacts, Integer> singleContactsDAO = null;
	private RuntimeExceptionDao<Reminder, Integer> reminderDAO = null;
	private RuntimeExceptionDao<WishList, Integer> wishListDAO = null;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, FacebookContacts.class);
			TableUtils.createTable(connectionSource, GmailContacts.class);
			TableUtils.createTable(connectionSource, PhoneContacts.class);
			TableUtils.createTable(connectionSource, SingleContacts.class);
			TableUtils.createTable(connectionSource, Reminder.class);
			TableUtils.createTable(connectionSource, WishList.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, FacebookContacts.class, true);
			TableUtils.dropTable(connectionSource, GmailContacts.class, true);
			TableUtils.dropTable(connectionSource, PhoneContacts.class, true);
			TableUtils.dropTable(connectionSource, SingleContacts.class, true);
			TableUtils.dropTable(connectionSource, Reminder.class, true);
			TableUtils.dropTable(connectionSource, WishList.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<FacebookContacts, Integer> getFacebookContactsDAO() {
		if (facebookContactsDAO == null) {
			facebookContactsDAO = getRuntimeExceptionDao(FacebookContacts.class);
		}
		return facebookContactsDAO;
	}
	
	public RuntimeExceptionDao<GmailContacts, Integer> getGmailContactsDAO() {
		if (gmailContactsDAO == null) {
			gmailContactsDAO = getRuntimeExceptionDao(GmailContacts.class);
		}
		return gmailContactsDAO;
	}
	
	public RuntimeExceptionDao<PhoneContacts, Integer> getPhoneContactsDAO() {
		if (phoneContactsDAO == null) {
			phoneContactsDAO = getRuntimeExceptionDao(PhoneContacts.class);
		}
		return phoneContactsDAO;
	}
	
	public RuntimeExceptionDao<SingleContacts, Integer> getSingleContactsDAO() {
		if (singleContactsDAO == null) {
			singleContactsDAO = getRuntimeExceptionDao(SingleContacts.class);
		}
		return singleContactsDAO;
	}
	
	public RuntimeExceptionDao<Reminder, Integer> getReminderDAO() {
		if (reminderDAO == null) {
			reminderDAO = getRuntimeExceptionDao(Reminder.class);
		}
		return reminderDAO;
	}
	
	public RuntimeExceptionDao<WishList, Integer> getWishListDAO() {
		if (wishListDAO == null) {
			wishListDAO = getRuntimeExceptionDao(WishList.class);
		}
		return wishListDAO;
	}
	
	@Override
	public void close() {
		super.close();
		
		facebookContactsDAO = null;
		gmailContactsDAO = null;
		phoneContactsDAO = null;
		singleContactsDAO = null;
	}
}
