package com.du.codewarriorcontact.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.du.codewarriorcontact.model.Contact;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

public class ContactExporter {
	
	public void Export(ArrayList<Contact> exportList, String filePath){
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(filePath));
			String [] headers = {"name", "phoneNumber", "email", "company", "job", "title" } ;
			writer.writeNext(headers);
			for(Contact c : exportList){
				ArrayList<String> row = new ArrayList<String>() ;
				row.add(c.firstName) ;
				row.add(c.phoneNumberHome) ;
				row.add(c.email) ;
				row.add(c.company);
				row.add(c.jobTitle);
				String [] strArray = new String[row.size()] ;
				strArray = row.toArray(strArray) ;
				writer.writeNext(strArray);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void Import(String fileName){
		
        try 
        {
        	// To ignore Processing of 1st row
	        CSVReader reader = new CSVReader(new FileReader(fileName), ',', '\"', 1);

	        ColumnPositionMappingStrategy<Contact> mappingStrategy 
	                                 = new ColumnPositionMappingStrategy<Contact>();
	        mappingStrategy.setType(Contact.class);

	        // the fields to bind do in your JavaBean
	        String[] columns = new String[] {"name","phoneNumber","Email","Company"};
	        mappingStrategy.setColumnMapping(columns);

	        CsvToBean<Contact> csv = new CsvToBean<Contact>();
	        List<Contact> contactList = csv.parse(mappingStrategy, reader);
	        
	        for (int i = 0; i < contactList.size(); i++) 
	        {
                Contact productDetail = contactList.get(i);
                // display CSV values
	        }
        }
        catch (FileNotFoundException e) 
        {
           System.err.println(e.getMessage());
        }
	}
	

		 
		}
