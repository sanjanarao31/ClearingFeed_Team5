import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import { User } from '../user';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user=new User();
  msg='';
  constructor(private _service:RegistrationService,private _router :Router) { }

  ngOnInit(): void {
  }
  loginUser(){
this._service.loginUserFromRemote(this.user).subscribe(
  data => {
    console.log("Response Received");
    this._router.navigate(["/loginsuccess"]);
   
  },

  error => {
    console.log("Exception Occurred");
    this.msg="Invalid email or password or both incorrect!!";
  
  }
)
  }
  gotoregistration(){
    this._router.navigate(["/registration"]);

  }

}
