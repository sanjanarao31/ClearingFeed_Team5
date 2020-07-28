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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		ArrayList wrongrecords = A.CreateSheet();
		System.out.println(wrongrecords);
		
				
		
		
	/*
	 public static boolean isAlphaNumeric(String s) 
    {
         return s.length() == 12 && s.matches("^[a-zA-Z0-9]*$");
    }

public static boolean isValidPattern(String d) 
    { 
        String regex = "^(1[0-2]|0[1-9])/(3[01]"
                       + "|[12][0-9]|0[1-9])/[0-9]{4}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher((CharSequence)d); 
        return matcher.matches(); 
    } 

public static int isPresentDate(String d) 
    {
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
         LocalDateTime now = LocalDateTime.now();
         String s= dtf.format(now);
         int res = d.compareTo(s);
         return res;
    }

public static void validateAmount(double amt)
{
     System.out.println( String.format( "%.2f", amt ) );
	
}

	 */
		return true;
		
	}

}
