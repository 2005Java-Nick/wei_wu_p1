import { Injectable } from '@angular/core';
import { Form } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { saveAs } from 'file-saver';
import { environment } from '../../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class FormService {

  private selectedForm: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  constructor(private http: HttpClient) { }


  uploadForm(fd: FormData) {
    this.http.post(environment.formDataURL, fd).subscribe(data => {
      console.log(data);
    });
  }


  public getForm(): Observable<any> {
    let params = new HttpParams();
    params = params.append('Token', sessionStorage.getItem('Token'));
    return this.http.get<any>(environment.mySubmissionsUrl, { params: params });
  }

  public getAdminForm(): Observable<any> {
    let params = new HttpParams();
    params = params.append('Token', sessionStorage.getItem('Token'));
    return this.http.get<any>(environment.adminUrl, { params: params });
  }
 
  getMySubmissions() {

  }

  onDownload(fileID: number, fileName: string) {
    let params = new HttpParams();
    params = params.append('Token', sessionStorage.getItem('Token'));
    params = params.append('FileID', fileID.toString());
    params = params.append('FileName', fileName);
    this.http.get(environment.fileDownloadUrl, {params: params, responseType: 'blob' }).subscribe(data => {
      saveAs(data, fileName);
    });
  }

  public getFormSelected(): Observable<number> {
    return this.selectedForm.asObservable();
  }
  public setFormSelected(value: number) {
    this.selectedForm.next(value);
  }

  upLoadMessage(fd: FormData) {
    this.http.post(environment.messageUploadUrl, fd).subscribe(data => {
     // console.log(data);
    });
  }

  getEmployeeInfo(): Observable<any> {
    let params = new HttpParams();
    params = params.append('Token', sessionStorage.getItem('Token'));
    return this.http.get<any>(environment.employeeInfoUrl, { params: params });
  }

  sendApproval(fd: FormData) {
    this.http.post(environment.approvalUrl, fd).subscribe(data => {
     // console.log(data);
    });
  }
}
