import { Component, OnInit } from '@angular/core';
import { FormService } from './service/form.service';

@Component({
  selector: 'app-user-interface',
  templateUrl: './user-interface.component.html',
  styleUrls: ['./user-interface.component.css']
})
export class UserInterfaceComponent implements OnInit {

  homepage = true;
  formpage = false;
  mysubmissions = false;
  admin = false;
  constructor(private formService: FormService) { }

  ngOnInit(): void {
  }
  onHomeClick() {
    this.closeAllPages();
    this.homepage = true;
  }
  onFormClick() {
    this.closeAllPages();
    this.formpage = true;
  }
  onMySubmissionsClick() {
    this.clearFormSelected();
    this.closeAllPages();
    this.mysubmissions = true;
  }
  onAdminClick() {
    this.closeAllPages();
    this.admin = true;
  }
  closeAllPages() {
    this.mysubmissions = false;
    this.homepage = false;
    this.formpage = false;
    this.admin = false;
  }
  clearFormSelected() {
    this.formService.setFormSelected(-1);
  }
}
