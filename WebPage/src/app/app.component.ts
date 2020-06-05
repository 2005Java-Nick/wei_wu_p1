import { Component } from '@angular/core';
import { LoginService } from './login/login/services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'my-appppp';

  constructor(private login: LoginService, private route: Router) { }


}
