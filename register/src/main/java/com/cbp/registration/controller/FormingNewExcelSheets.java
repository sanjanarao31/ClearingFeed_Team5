package com.cbp.registration.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import  com.cbp.registration.controller.Validation;
import com.cbp.registration.controller.InputtingFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class FormingNewExcelSheets 
{
	private static String[] columns = {"PayeeAccount","TransactionReference","PayeeName","Amount","ValueDate","PayerName","PayerAccount"};
	public static List<Object> CreateSheet() 
	{
		JSONArray temp = new JSONArray();
		Validation B = new Validation();
		JSONArray a = B.ConvertToJson();
		 
		List<Object> finans = B.RecordValidation(a);
		
		XSSFWorkbook workbook = new XSSFWorkbook(); 
	    
	    XSSFSheet sheet = workbook.createSheet("Final_Correct_Report");
	    Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        XSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
	    for(int i =0;i<finans.size() - 1;i++) 
		{
	    	Row row = sheet.createRow(rowNum++);
		JSONObject object = (JSONObject) ((ArrayList) finans.get(0)).get(i);// your json object
		for(int j=0;j<columns.length;j++)
		{
			int cellnum = 0;
			
			
		for (Object key : object.keySet()) 
		{
		     Cell cell = row.createCell(cellnum++);
		    cell.setCellValue(object.get(key).toString());
			
		}
			
      } 
	   
	}
	    
	for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
	    try
	    {
	        //Write the workbook in file system
	    	String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
	        FileOutputStream out = new FileOutputStream(new File("Final_Report" + fileName + ".xlsx"));
	        workbook.write(out);
	        out.close();
	        System.out.println("Final report written successfully on disk.");
	        return finans;
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        return null;
	    }
	    
	}
	
}
