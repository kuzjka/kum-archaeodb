import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

// Angular material
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from "@angular/material/toolbar";

// Routing
import { AppRoutingModule } from "./app-routing.module";
import { MatButtonModule } from "@angular/material/button";
import {HttpClientModule} from "@angular/common/http";
import {CategoryService} from "./category.service";



@NgModule({
  declarations: [
    AppComponent,

  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    // Material
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule
  ],
providers:[CategoryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
