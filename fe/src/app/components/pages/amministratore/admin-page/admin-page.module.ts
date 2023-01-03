import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPageComponent } from './admin-page.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { RouterModule, Routes } from '@angular/router';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';

const routes: Routes = [
  { path: '', component: AdminPageComponent }
];

@NgModule({
  declarations: [AdminPageComponent],
  imports: [
    CommonModule,
    FullCalendarModule,
    MessageModule,
    MessagesModule,
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
export class AdminPageModule { }
