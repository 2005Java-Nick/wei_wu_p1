import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeInterfaceComponent } from './employee-interface/employee-interface.component';
import { AdminInterfaceComponent } from './admin-interface/admin-interface.component';
import { UserInterfaceComponent } from './user-interface.component';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatIconModule} from '@angular/material/icon';
import { TuitionFormComponent } from './tuition-form/tuition-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { MySubmissionsComponent } from './my-submissions/my-submissions.component';
import { FormatingPipePipe } from './formating-pipe.pipe';


@NgModule({
  declarations: [UserInterfaceComponent, EmployeeInterfaceComponent, AdminInterfaceComponent, TuitionFormComponent, HomePageComponent, MySubmissionsComponent, FormatingPipePipe],
  imports: [
    CommonModule,
    NgbModule,
    FormsModule,
    MatIconModule
  ],
  exports: [
    UserInterfaceComponent
  ]
})
export class UserInterfaceModule { }
