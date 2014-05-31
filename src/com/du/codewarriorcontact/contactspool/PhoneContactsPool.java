package com.du.codewarriorcontact.contactspool;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

import com.du.codewarriorcontact.database.CWDAO;
import com.du.codewarriorcontact.database.PhoneContacts;
import com.du.codewarriorcontact.database.SingleContacts;
import com.du.codewarriorcontact.model.Contact;
import com.du.codewarriorcontact.notification.NotificationUtility;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

public class PhoneContactsPool implements IContactsPool {
	 
	/*
	 * Singleton
	 */
	private static PhoneContactsPool instance = null ;
	public PhoneContactsPool(){
		
	}
	public static PhoneContactsPool getInstance(){
		if (instance == null)
			instance = new PhoneContactsPool() ;
		return instance ;
	}
	

	public int CreateContact(Context ctx, Contact ct){
		 String DisplayName = ct.firstName + " " + ct.lastName;
		 String MobileNumber = ct.phoneNumberMobile;
		 String HomeNumber = ct.phoneNumberHome;
		 String WorkNumber = ct.phoneNumberWork;
		 String emailID = ct.email;
		 String company = ct.company;
		 String jobTitle = ct.jobTitle;
		
		 ArrayList <ContentProviderOperation > ops = new ArrayList<ContentProviderOperation> ();
		
		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
			 .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
			 .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
			 .build());
		
		 //------------------------------------------------------ Names
		 if (DisplayName != null) {
			 ops.add(ContentProviderOperation.newInsert(
			 ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				 .withValue(
				 ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
				 DisplayName).build());
		 }
		
		 //------------------------------------------------------ Mobile Number
		 if (MobileNumber != null) {
			 ops.add(ContentProviderOperation.
				 newInsert(ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
				 .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
				 ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				 .build());
		 }
		
		 //------------------------------------------------------ Home Numbers
		 if (HomeNumber != null) {
			 ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
				 .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
				 ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
				 .build());
		 }
		
		 //------------------------------------------------------ Work Numbers
		 if (WorkNumber != null) {
			 ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
				 .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
				 ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
				 .build());
		 }
		
		 //------------------------------------------------------ Email
		 if (emailID != null) {
			 ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
				 .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
				 ContactsContract.CommonDataKinds.Email.TYPE_WORK)
				 .build());
		 }
		
		 //------------------------------------------------------ Organization
		 if (company != null && !company.equals("") && 
				 jobTitle != null && !jobTitle.equals("")) {
			 ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				 .withValue(ContactsContract.Data.MIMETYPE,
				 ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				 .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY,
				 company)
				 .withValue(ContactsContract.CommonDataKinds.Organization.TYPE,
				 ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				 .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
				 .withValue(ContactsContract.CommonDataKinds.Organization.TYPE,
				 ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				 .build());
		 }
		 if(ct.photo != null) {
			 ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 ct.photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
			 byte[] byteArray = stream.toByteArray();
			 
			 ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					 .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					 .withValue(ContactsContract.Data.MIMETYPE,
					 ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
					 .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, byteArray).build());
		 }
		
		 // Asking the PhoneContacts provider to create a new PhoneContacts
		 try {
			 ContentProviderResult [] res = (ctx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops));
			 String resTemp = res[0].uri.toString() ;
//			 Log.e("ID IN URI", Integer.parseInt(resTemp) + "") ;
			 resTemp = resTemp.substring(resTemp.lastIndexOf("/") + 1) ;
			 int cid = Integer.parseInt(resTemp) ;
			 
			 PhoneContacts pc = new PhoneContacts(ct.id, ct.firstName, ct.lastName,
						ct.email, ct.phoneNumberMobile, ct.phoneNumberHome, ct.phoneNumberWork,
						ct.phoneNumberOther, ct.note, ct.address, ct.company, ct.jobTitle, ct.group , ct.id);
				CreateOrUpdateStatus st =  CWDAO.getCWdao().getPhoneContactsDAO().createOrUpdate(pc);
				CWDAO.getCWdao()
						.getSingleContactsDAO()
						.createOrUpdate(
								new SingleContacts(pc.getCid(), "", ""));
				
		 } catch (Exception e) {
			 e.printStackTrace();
		 
		 }
		 return 0;
		 
	 }

	@Override
	public void SyncAllContact(Context ctx) {
		int countUpdated = 0 ;
		PhoneContacts lastUpdated = null ;
		ContentResolver cr = ctx.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				// String firstName =
				// cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
				// String lastName =
				// cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String phoneMobile = "";
				String phoneHome = "";
				String phoneWork = "";
				String phoneOther = "";
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);

					while (pCur.moveToNext()) {
						int phoneType = pCur
								.getInt(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
						String phoneNumber = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						switch (phoneType) {
						case Phone.TYPE_MOBILE:
							phoneMobile = phoneNumber;
							Log.e(name + "(mobile number)", phoneNumber);
							break;
						case Phone.TYPE_HOME:
							phoneHome = phoneNumber;
							Log.e(name + "(home number)", phoneNumber);
							break;
						case Phone.TYPE_WORK:
							phoneWork = phoneNumber;
							Log.e(name + "(work number)", phoneNumber);
							break;
						case Phone.TYPE_OTHER:
							phoneOther = phoneNumber;
							Log.e(name + "(other number)", phoneNumber);
							break;
						default:
							break;
						}
					}
					pCur.close();
				}
				Cursor emailCur = cr.query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
								+ " = ?", new String[] { id }, null);

				String fullEmail = "";
				while (emailCur.moveToNext()) {
					// This would allow you get several email addresses
					// if the email addresses were stored in an array
					String email = emailCur
							.getString(emailCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					String emailType = emailCur
							.getString(emailCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
					fullEmail += email + " ";
				}
				emailCur.close();

				String orgWhere = Contacts.ContactMethods.PERSON_ID + " = ?";
				String[] orgWhereParams = new String[] { id };
				Cursor orgCur = cr.query(Contacts.Organizations.CONTENT_URI,
						null, orgWhere, orgWhereParams, null);
				String orgName = "";
				String jobTitle = "";
				if (orgCur.moveToFirst()) {
					orgName = orgCur.getString(orgCur
							.getColumnIndex(Contacts.Organizations.COMPANY));
					// jobTitle = orgCur.getString(
					// orgCur.getColumnIndex(Contacts.));
				}
				orgCur.close();
				Uri contactUri = ContentUris.withAppendedId(
						ContactsContract.Contacts.CONTENT_URI,
						Long.parseLong(id));
				Uri photoUri = Uri.withAppendedPath(contactUri,
						ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

				// notes
				String noteWhere = ContactsContract.Data.CONTACT_ID
						+ " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
				String[] noteWhereParams = new String[] { id,
						ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE };
				Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI,
						null, noteWhere, noteWhereParams, null);
				String note = "";
				if (noteCur.moveToFirst()) {
					note = noteCur
							.getString(noteCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
					System.out.println("Note " + note);
				}
				noteCur.close();

				String addrWhere = ContactsContract.Data.CONTACT_ID
						+ " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
				String[] addrWhereParams = new String[] {
						id,
						ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE };
				Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
						null, null, null, null);

				String address = "";
				if (addrCur.moveToNext()) {
					String poBox = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
					address += poBox + " ";
					String street = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
					address += poBox + " ";
					String city = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
					address += city + " ";
					String state = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
					address += state + " ";
					String postalCode = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
					address += postalCode + " ";
					String country = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
					address += country + " ";
					String type = addrCur
							.getString(addrCur
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
					address += type;
				}
				addrCur.close();
				PhoneContacts pc = new PhoneContacts(id, name, "",
						fullEmail, phoneMobile, phoneHome, phoneWork,
						phoneOther, note, address, orgName, jobTitle, "", id);
				Log.e("ADDED", "CONTACT " + pc.getFirstName());
				CreateOrUpdateStatus st =  CWDAO.getCWdao().getPhoneContactsDAO().createOrUpdate(pc);
				countUpdated += st.getNumLinesChanged() ;
				lastUpdated = pc ;
				CWDAO.getCWdao()
						.getSingleContactsDAO()
						.createOrUpdate(
								new SingleContacts(pc.getCid(), "", ""));
			}
			cur.close();
		}
		if(countUpdated > 1){
			NotificationUtility.NotifyMultipleContacts(ctx, countUpdated);
		} else if(countUpdated == 1){
			NotificationUtility.NotifyNewcontact(ctx,lastUpdated);
		}
	}

	@Override
	public void DeleteContact(Context ctx, int id) {
		ctx.getContentResolver().delete(RawContacts.CONTENT_URI, RawContacts._ID+"=?", new String[]{id + ""});
	}
	
	// public void EditContact(PhoneContacts PhoneContacts){
	// String DisplayName = PhoneContacts.name;
	// String MobileNumber = PhoneContacts.phoneNumber;
	// String HomeNumber = PhoneContacts.phoneNumberHome;
	// String WorkNumber = PhoneContacts.phoneNumberWork;
	// String emailID = PhoneContacts.email;
	// String company = PhoneContacts.company;
	// String jobTitle = PhoneContacts.jobTitle;
	//
	// int id = 1;
	// String firstname = PhoneContacts.name.split(" ")[0];
	// String lastname = PhoneContacts.name.split(" ")[1];
	//
	// //String photo_uri =
	// "android.resource://com.my.package/drawable/default_photo";
	//
	// ArrayList<ContentProviderOperation> ops = new
	// ArrayList<ContentProviderOperation>();
	//
	// // Name
	// Builder builder =
	// ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
	// builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " +
	// ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id),
	// ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
	// builder.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
	// lastname);
	// builder.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
	// firstname);
	// ops.add(builder.build());
	//
	// // Number
	// builder =
	// ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
	// builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " +
	// ContactsContract.Data.MIMETYPE + "=?"+ " AND " +
	// ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new
	// String[]{String.valueOf(id),
	// ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
	// String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)});
	// builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
	// PhoneContacts.phoneNumber);
	// ops.add(builder.build());
	//
	//
	// // Picture
	// // try
	// // {
	// // Bitmap bitmap =
	// MediaStore.Images.Media.getBitmap(getContentResolver(),
	// Uri.parse(photo_uri));
	// // ByteArrayOutputStream image = new ByteArrayOutputStream();
	// // bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
	// //
	// // builder =
	// ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
	// // builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" +
	// " AND " + ContactsContract.Data.MIMETYPE + "=?", new
	// String[]{String.valueOf(id),
	// ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE});
	// // builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,
	// image.toByteArray());
	// // ops.add(builder.build());
	// // }
	// // catch (Exception e)
	// // {
	// // e.printStackTrace();
	// // }
	//
	// // Update
	// try
	// {
	// ctx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	//
	//
	//

	// public PhoneContacts GetByUri(Uri uri){
	// ContentResolver cr = ctx.getContentResolver() ;
	// //Cursor cc = cr.query(uri, null, null, "", null) ; //(uri, null, null) ;
	//
	// }

}
