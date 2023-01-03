import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UtenteComponent } from '../utente.component';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { CalendarModule } from 'primeng/calendar';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {PasswordModule} from 'primeng/password';
import {DividerModule} from 'primeng/divider';
import {DropdownModule} from 'primeng/dropdown';
import {TableModule} from 'primeng/table';
const routes: Routes = [
  { path: '', component: UtenteComponent }
];

@NgModule({
  declarations: [UtenteComponent],
  imports: [
    CommonModule,
    ButtonModule,
    InputTextModule,
    InputTextareaModule,
    PasswordModule,
    FileUploadModule,
    CalendarModule,
    MessagesModule,
    MessageModule,
    FormsModule,
    DividerModule,
    ReactiveFormsModule,
    DropdownModule,
    TableModule,
    RouterModule.forChild(routes),
    TranslateModule.forChild({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
  })
  ]
})
export class UtenteComponenteModuleModule { }
