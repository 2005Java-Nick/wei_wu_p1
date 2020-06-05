import { Component, OnInit } from '@angular/core';
import { NgbDateStruct, NgbCalendar } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { FormService } from '../service/form.service';
import { TuitionReimbForm } from '../types/TuitionReimbForm';
import { min } from 'rxjs/operators';

@Component({
  selector: 'app-tuition-form',
  templateUrl: './tuition-form.component.html',
  styleUrls: ['./tuition-form.component.css']
})
export class TuitionFormComponent implements OnInit {
  model: NgbDateStruct;
  files: File[] = [];
  tuitionForm: TuitionReimbForm;
  anyobj: any;
  eventTypePercent: number;
  projectedAmount: number;
  datePickerColor: string;
  
  minDate = {year: new Date().getFullYear(), month: new Date().getMonth()+1, day: new Date().getDate()+7};


  constructor(private http: HttpClient, private formService: FormService) { }

  ngOnInit(): void {
    this.datePickerColor = 'red';
    this.eventTypePercent = 0;
    this.tuitionForm = new TuitionReimbForm();
    this.tuitionForm.eventCost = 0;
    this.projectedAmount = 0;
    this.getMyInfo();
  }



  onClick() {
    this.tuitionForm.eventDate = (this.model.year.toString()
      + '-' + this.padLeadingZero(this.model.month).toString()
      + '-' + this.padLeadingZero(this.model.day).toString());
    this.uploadForm();
  }
  padLeadingZero(n: number) {
    return (n < 10) ? ('0' + n) : n;
  }


  onFileSelected(event) {
    for (let i = 0; i < event.target.files.length; i++) {
      this.files.push(event.target.files[i]);
    }
    console.log(this.files);
  }
  removeFileAt(i) {
    this.files.splice(i, 1);
  }


  uploadForm() {
    this.tuitionForm.eventType = this.eventTypePercent.toString();
    const fd = new FormData();
    fd.append('firstName', this.tuitionForm.firstName);
    fd.append('midName', this.tuitionForm.midName);
    fd.append('lastName', this.tuitionForm.lastName);
    fd.append('address', this.tuitionForm.address);
    fd.append('addressTwo', this.tuitionForm.addressTwo);
    fd.append('city', this.tuitionForm.city);
    fd.append('state', this.tuitionForm.state);
    fd.append('zip', this.tuitionForm.zip);
    fd.append('eventLocation', this.tuitionForm.eventLocation);
    fd.append('eventDiscription', this.tuitionForm.eventDiscription);
    fd.append('eventCost', this.tuitionForm.eventCost.toString());
    fd.append('eventType', this.tuitionForm.eventType);
    fd.append('justification', this.tuitionForm.justification);
    fd.append('workTimeOff', this.tuitionForm.workTimeOff);
    fd.append('gradingFormat', this.tuitionForm.gradingFormat);
    fd.append('cutOffGrade', this.tuitionForm.cutOffGrade);
    fd.append('optionalComments', this.tuitionForm.optionalComments);
    fd.append('eventDate', this.tuitionForm.eventDate);
    fd.append('Token', sessionStorage.getItem('Token'));
    console.log(sessionStorage.getItem('Token'));
    for (let i = 0; i < this.files.length; i++) {
      fd.append('File[' + i + ']', this.files[i], this.files[i].name);
    }
    this.formService.uploadForm(fd);
  }

  getMyInfo() {
    this.formService.getEmployeeInfo().subscribe(data => {
      this.tuitionForm.firstName = data.EmployeeInfo.firstName;
      this.tuitionForm.midName = data.EmployeeInfo.midName;
      this.tuitionForm.lastName = data.EmployeeInfo.lastName;
      this.tuitionForm.phone = data.EmployeeInfo.phone;
      this.tuitionForm.email = data.EmployeeInfo.email;
      this.tuitionForm.address = data.EmployeeInfo.address;
      this.tuitionForm.addressTwo = data.EmployeeInfo.addressTwo;
      this.tuitionForm.city = data.EmployeeInfo.city;
      this.tuitionForm.state = data.EmployeeInfo.state;
      this.tuitionForm.zip = data.EmployeeInfo.zip;
      this.tuitionForm.reimbursementAvaliable = data.EmployeeInfo.reimbursementAvaliable;
      this.tuitionForm.reimbursementPending = data.EmployeeInfo.reimbursementPending;
      console.log(this.tuitionForm);
    });
  }

  onPickedDate() {
    this.datePickerColor = 'white';
  }

  onEventType(num: number) {
    this.eventTypePercent = num;
    console.log(this.eventTypePercent);
  }
  calProjected() {
    if (this.tuitionForm.eventCost === 0) {
      this.projectedAmount = 0;
    }
    if ((this.tuitionForm.eventCost * (this.eventTypePercent / 100)) > (this.tuitionForm.reimbursementAvaliable - this.tuitionForm.reimbursementPending)) {
      this.projectedAmount = (this.tuitionForm.reimbursementAvaliable - this.tuitionForm.reimbursementPending);
    } else {

      this.projectedAmount = (this.tuitionForm.eventCost * (this.eventTypePercent / 100));
    }
  }
}
