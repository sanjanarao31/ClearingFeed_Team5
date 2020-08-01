package com.cbp.registration.controller;
import com.cbp.registration.controller.FormingNewExcelSheets;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cbp.registration.model.TransactionRecord;
import com.cbp.registration.model.User;
import com.cbp.registration.service.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.cbp.registration.controller"})

@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService service;
	
	@PostMapping("/registeruser")
	@CrossOrigin(origins = "http://localhost:4200")
	
	public User registerUser(@RequestBody User user) throws Exception {
		String tempEmailId=user.getEmailId();
		User userobj=null;
		if(tempEmailId !=null && "".equals(tempEmailId)) {
			 userobj =service.fetchUserByEmailId(tempEmailId);
			if(userobj != null) {
				throw new Exception("user with "+tempEmailId+" already present");
			}
		}
		User userObj =null;
		userObj=service.saveUser(user);
		return userObj;
		
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public User loginUser(@RequestBody User user) throws Exception {
		String tempEmailId=user.getEmailId();
		String tempPass=user.getPassword();
		User userobj=null;

		if(tempEmailId !=null && tempPass!=null) {
		 userobj=service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if(userobj==null) {
			throw new Exception("User not found");
		}
		return userobj;
			
	}
	
	
	
	@PostMapping("/upload")
	@CrossOrigin(origins = "http://localhost:4200")
	
	public Boolean validateFile(@RequestParam("excelFile") MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {
		System.out.println(file.getOriginalFilename());
		
		
		String destination = "C:/Users/Admin/Desktop/register/target"+ file.getOriginalFilename();
	    File savefile = new File(destination);
	    file.transferTo(savefile);
	    FormingNewExcelSheets A = new FormingNewExcelSheets();
	    List<Object> listOfJsonAndRecords = A.CreateSheet();
	    for(int i =0;i<listOfJsonAndRecords.size()-1;i++) 
		{
	    	
			JSONObject object = (JSONObject) ((ArrayList) listOfJsonAndRecords.get(0)).get(i);
			System.out.println(object);
		}
	    System.out.println(((ArrayList) listOfJsonAndRecords.get(listOfJsonAndRecords.size()-1)));
		
				
		

		return true;
		
	}
	
	@RequestMapping("/afterupload")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Object> fetchRecord() throws Exception {
		FormingNewExcelSheets A = new FormingNewExcelSheets();
		List<TransactionRecord> rows = new ArrayList<TransactionRecord>();
		List<Object> listOfJsonAndRecords = A.CreateSheet();
		
			for(int i =0;i<listOfJsonAndRecords.size() - 1;i++) 
			{
		    	
				JSONObject object = (JSONObject) ((ArrayList) listOfJsonAndRecords.get(0)).get(i);
				
			}
		
		
		return listOfJsonAndRecords;
		
		
		
		
			
	}

}
