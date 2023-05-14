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
import {ItemsService} from "./items.service";



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
providers:[],
  bootstrap: [AppComponent]
})
export class AppModule { }
