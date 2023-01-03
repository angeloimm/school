import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminGuard } from './guards/application-guard';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { MessageService } from 'primeng/api';
import { TopBarComponent } from './components/layout/top-bar/top-bar.component';
import { LayoutService } from './components/layout/service/layout.service';
import { FormUtilsService } from './shared/services/form-utils.service';
import {DialogModule} from 'primeng/dialog';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import { LoaderDialogInterceptor } from './shared/interceptors/loader.dialog.interceptor';
import {MenubarModule} from 'primeng/menubar';
import { UtentePageComponent } from './components/pages/utente/utente-page/utente-page.component';

/**
 * CONFIG la gestione dell'internazionalizzazione;
 */
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    HttpClientXsrfModule,
    MenubarModule,
    DialogModule,
    ProgressSpinnerModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    AdminGuard,
    MessageService,
    LayoutService,
    FormUtilsService,
    {provide: HTTP_INTERCEPTORS, useClass: LoaderDialogInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
