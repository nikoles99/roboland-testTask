import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {DatePipe} from '@angular/common';
import {LoggingComponent} from './logging/logging.component';
import {RobotsComponent} from './robots/robots.component';
import {HttpClientModule} from "@angular/common/http";
import {RobotsService} from "./robots/robots.service";

@NgModule({
  declarations: [
    AppComponent,
    LoggingComponent,
    RobotsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [DatePipe, RobotsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
