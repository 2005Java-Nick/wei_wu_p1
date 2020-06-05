import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormService } from '../service/form.service';
import { TuitionReimbForm } from '../types/TuitionReimbForm';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Observable, interval, Subscription} from 'rxjs';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-admin-interface',
  templateUrl: './admin-interface.component.html',
  styleUrls: ['./admin-interface.component.css']
})
export class AdminInterfaceComponent implements OnInit, OnDestroy {
  mySubmissionForms: any;
  myFormSelected: number;
  messageToAdd: string;
  subscription: Subscription;

  constructor(private formService: FormService) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  ngOnInit(): void {
    this.myFormSelected = -1;
    this.formService.getFormSelected().subscribe(data => {
      this.myFormSelected = data;
    });
    this.messageToAdd = '';
    this.mySubmissionForms = null;
    this.getSubmissions();
    const source = interval(10000);
    this.subscription = source.subscribe(val => 
     this.getSubmissions()
     );
  }
  getSubmissions() {
    this.formService.getAdminForm().pipe(first()).subscribe(data => {
      this.mySubmissionForms = data;
      console.log(this.mySubmissionForms.SubForms[1]);
    });
  }

  selectForm(n: number) {
    this.myFormSelected = n;
  }

  downloadSelectedFile(fileID: number, fileName: string) {
    console.log(fileID + '|||' + fileName);
    this.formService.onDownload(fileID, fileName);
  }

  onBack() {
    this.myFormSelected = -1;
  }
  
  
  uploadMessage() {
    
    if (!(this.messageToAdd === '') && this.messageToAdd) {
      const fd = new FormData();
      fd.append('FormID', this.mySubmissionForms.SubForms[this.myFormSelected].formID.toString());
      fd.append('Message', this.messageToAdd);
      fd.append('Token', sessionStorage.getItem('Token'));
      console.log(sessionStorage.getItem('Token'));
      console.log(this.messageToAdd);
      console.log(this.mySubmissionForms.SubForms[this.myFormSelected].formID.toString());
      this.formService.upLoadMessage(fd);


    }
  }

  onApproval(approval: string){
      const fd = new FormData();
      fd.append('FormID', this.mySubmissionForms.SubForms[this.myFormSelected].formID.toString());
      fd.append('Approval', approval);
      fd.append('Token', sessionStorage.getItem('Token'));
      console.log(sessionStorage.getItem('Token'));
      console.log(this.mySubmissionForms.SubForms[this.myFormSelected].formID.toString());
      this.formService.sendApproval(fd);
  }

}
