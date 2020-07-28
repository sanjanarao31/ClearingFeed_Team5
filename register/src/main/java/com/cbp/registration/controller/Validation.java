package com.cbp.registration.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;    
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import com.cbp.registration.controller.InputtingFile;
public class Validation {

	 public static boolean checkAlphaNumeric(String s)
		    {
		 		String result = String.format("%.0f", Double.parseDouble(s));
		 		return result.length() == 12 && result.matches("^[a-zA-Z0-9]*$");
		    }

		
		public static boolean checkName(String s) {
			s = s.toLowerCase();
		      char[] charArray = s.toCharArray();
		      for (int i = 0; i < charArray.length; i++) {
		         char ch = charArray[i];
		         if (!(ch >= 'a' && ch <= 'z')) {
		            return false;
		         }
		      }
		      return true;
		}
		

		public static boolean validateAmount(String amt)
		{
			
			try
			{
				if(amt==null){
				  return false;
			  }
				
				else if(Double.parseDouble(amt)>0) {
			  
					return true;
			  }
			  
			  else
			  {
				  return false;
			  }
				
				
			}
			catch(NumberFormatException e)
			{
			  return false;
			}

		}
		public static boolean isValidDateFormat(String format, String value, Locale locale) {
		    LocalDateTime ldt = null;
		    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

		    try {
		        ldt = LocalDateTime.parse(value, fomatter);
		        String result = ldt.format(fomatter);
		        return result.equals(value);
		    } catch (DateTimeParseException e) {
		        try {
		            LocalDate ld = LocalDate.parse(value, fomatter);
		            String result = ld.format(fomatter);
		            return result.equals(value);
		        } catch (DateTimeParseException exp) {
		            try {
		                LocalTime lt = LocalTime.parse(value, fomatter);
		                String result = lt.format(fomatter);
		                return result.equals(value);
		            } catch (DateTimeParseException e2) {
		                
		            }
		        }
		    }

		    return false;
		}
		

public List<Object> RecordValidation(JSONArray array) 
{
	List<Object> finalAns = new ArrayList<Object>();
	JSONObject correct = new JSONObject();
	JSONArray a = new JSONArray();
	ArrayList wrong = new ArrayList();
	
	for(int i =0;i<array.size();i++) 
	{
	JSONObject object = (JSONObject) array.get(i); // your json object
	int count =0;
	for (Object key : object.keySet()) 
	{
		if(key.toString().equals("Transaction Reference")) 
		{
			boolean t = checkAlphaNumeric(object.get(key).toString());
			if(t==true) 
			{
				count++;
				//correct.put("Transaction Reference", object.get(key).toString());
				
			}
			else 
			{
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Value Date"))
		{
			boolean t=isValidDateFormat("ddMMyyyy",object.get(key).toString() , Locale.ENGLISH);
			if(t==true) 
			{
				count++;
				//correct.put("Value Date", object.get(key).toString());
			}
			else 
			{
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Payer Name")) 
		{
			boolean t = checkName(object.get(key).toString());
			if(t==true) 
			{
				count++;
				//correct.put("Payer Name", object.get(key).toString());
			}
			else {
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Payer Account"))
		{
			boolean t = checkAlphaNumeric(object.get(key).toString());
			if(t==true) 
			{
				count++;
				//correct.put("Payer Account", object.get(key).toString());
			}
			else 
			{
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Payee Name")) 
		{
			boolean t = checkName(object.get(key).toString());
			if(t==true) 
			{
				count++;
				//correct.put("Payee Name", object.get(key).toString());
			}
			else {
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Payee Account"))
		{
			boolean t = checkAlphaNumeric(object.get(key).toString());
			if(t==true) 
			{
				count++;
				//correct.put("Payee Account", object.get(key).toString());
			}
			else 
			{
				wrong.add(object.get("Transaction Reference").toString());
			}
		}
		if(key.toString().equals("Amount"))
		{
			if(object.get(key)==null) {
				wrong.add(object.get("Transaction Reference").toString());
			}
			else {
			boolean t = validateAmount(object.get(key).toString());
			if(t==true)
			{
				count++;
				//correct.put("Amount", object.get(key).toString());
			}
			else
			{
				wrong.add(object.get("Transaction Reference").toString());
			}
			}
		}
		
		
	}
	if(count == 7) 
	{
		a.add(object);
		finalAns.add(a);
	}
	}
	
	finalAns.add(wrong);
	return finalAns;
	
}
	
public JSONArray ConvertToJson()
{
	InputtingFile A = new InputtingFile();
	FileInputStream file;
	
	JSONArray array = new JSONArray();
	
	try {
		file = new FileInputStream(new File("C:\\Users\\Admin\\Desktop\\register\\targetSampleFile.xlsx"));
		XSSFSheet st = A.readfile(file);
		for(int i=1;i<st.getPhysicalNumberOfRows();i++) {
			JSONObject Obj=new JSONObject(); 
			for(int j=0;j<st.getRow(0).getPhysicalNumberOfCells();j++) 
			{
				Cell t1 = A.readCellData(st,i,j);
				if(j==0 || j==3 || j==5)
				{
					String result = String.format("%.0f", Double.parseDouble(t1.toString()));
					if(j==0) {
						Obj.put("Transaction Reference",result);
					}
					 if(j==3) {
						Obj.put("Payer Account",result);
					 }
					 if(j==5) {
						Obj.put("Payee Account",result);
					 }
				}
				else if(j==2 || j==4) 
				{
					if(j==2) {
						Obj.put("Payer Name",t1.toString());
						}
						if(j==4) {
							Obj.put("Payee Name",t1.toString());
						}
					
				}
				else if(j==6)
				{
					if(t1==null) {
						Obj.put("Amount", t1);
					}
					else 
					{
						String result = String.format("%.0f", Double.parseDouble(t1.toString()));
						Obj.put("Amount",result); 
					}
				}
				else if(j==1) 
				{
					String result = String.format("%.0f", Double.parseDouble(t1.toString()));
					Obj.put("Value Date", result);
				}
				
				
			}
			array.add(Obj);
			
			
			
			
		}
		
		return array;
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
	
}



}
