import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { browser, element, by, ExpectedConditions, protractor} from 'protractor'
import { Alert, WebElement, Button } from 'selenium-webdriver';
import * as XLSX from 'xlsx';
import { WSAEFAULT } from 'constants';
import readXlsxFile from 'read-excel-file';
@Component({
  selector: 'app-loginsuccess',
  templateUrl: './loginsuccess.component.html',
  styleUrls: ['./loginsuccess.component.css']
  
})
export class LoginsuccessComponent implements OnInit {

  constructor(private httpClient: HttpClient) { }
  selectedFile: File;
  message: string;
  ngOnInit(): void {
    
  }

  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];

  }


  onUpload() {
    console.log(this.selectedFile);
    
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadFileData = new FormData();
    uploadFileData.append('excelFile', this.selectedFile,'SampleFile.xlsx');
    
  
    //Make a call to the Spring Boot Application to save the file
    this.httpClient.post('http://localhost:9000/upload', uploadFileData, { observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.message = 'File uploaded successfully';
        } else {
          this.message = 'file not uploaded successfully';
        }

        
       
      }
      );
      


  }

}
