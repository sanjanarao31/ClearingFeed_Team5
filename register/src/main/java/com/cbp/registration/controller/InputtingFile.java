package com.cbp.registration.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InputtingFile
{
	@SuppressWarnings("resource")
	public XSSFSheet readfile(FileInputStream file) 
	{
		 
        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        return sheet;
	}
	public Cell readCellData(XSSFSheet sheet,int r,int c) 
	{
		try {
			Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
             
            while (cellIterator.hasNext()) 
            {
                Cell cell = cellIterator.next();
                //Check the cell type and format accordingly
                
				row=sheet.getRow(r); //returns the logical row  
				cell=row.getCell(c);
				return cell;
				
				   
				  
				
            }
            }
        
        return null;
	}catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
        
        
	}
    public static void main(String[] args) 
    {
    	
    	
    }
}