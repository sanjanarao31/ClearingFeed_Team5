import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
@Component({
  selector: 'app-afterupload',
  templateUrl: './afterupload.component.html',
  styleUrls: ['./afterupload.component.css']
})
export class AfteruploadComponent implements OnInit {
  right=[];
  wrong = []
  
  url='http://localhost:9000/afterupload'
  constructor(private httpClient: HttpClient) { 
   
    this.httpClient.get(this.url).toPromise().then(data =>{
      console.log(data);
      for(let key in data)
        if(data.hasOwnProperty(key))
          this.right.push(data[key])
    this.wrong = this.right.slice(this.right.length-1)
    this.right = this.right.slice(0,this.right.length -1)


    
    }

    );


  
  }


  
  ngOnInit(): void {


  }

 
}
