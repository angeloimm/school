import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericErrorComponent } from './generic-error.component';
import { RouterModule, Routes } from '@angular/router';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';

const routes: Routes = [
  { path: '', component: GenericErrorComponent }
];

@NgModule({
  declarations: [GenericErrorComponent],
  imports: [
    CommonModule,
    MessageModule,
    MessagesModule,
    RouterModule.forChild(routes),
    TranslateModule.forChild({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
  }),
  ]
})
export class GenericErrorModule { }
