import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {SignupComponent} from './signup/signup.component';
import {HeaderComponent} from './header/header.component';
import {PostsService} from './shared/posts.service';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [AppComponent, HomeComponent, SignupComponent, HeaderComponent],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [PostsService],
  bootstrap: [AppComponent]
})
export class AppModule {}
