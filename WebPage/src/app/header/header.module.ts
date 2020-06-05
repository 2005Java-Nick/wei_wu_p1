import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HeaderBarComponent } from './header-bar/header-bar.component';



@NgModule({
  declarations: [NavBarComponent, HeaderBarComponent],
  imports: [
    CommonModule
  ],
  exports: [
    HeaderBarComponent
  ]
})
export class HeaderModule { }
