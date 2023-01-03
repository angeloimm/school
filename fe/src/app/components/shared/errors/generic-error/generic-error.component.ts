import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Message } from 'primeng/api';

@Component({
  selector: 'app-generic-error',
  templateUrl: './generic-error.component.html',
  styleUrls: ['./generic-error.component.scss']
})
export class GenericErrorComponent implements OnInit {
  msgs:Message[] = [];
  constructor(private translate: TranslateService,
              private activatedRoute: ActivatedRoute
            ){

  }
  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      let tipoErrore = params['tipoErrore'];
      const welcomMsg:Message = {};
      //Se tipo errore != null --> o 403 o 404
      if( tipoErrore !== null && tipoErrore !=='' ){
        welcomMsg.summary = this.translate.instant('generic-error.'+tipoErrore+'.summary');
        welcomMsg.detail = this.translate.instant('generic-error.'+tipoErrore+'.detail');
        welcomMsg.severity = 'warning';
        this.msgs.push(welcomMsg);
      }else{
        welcomMsg.summary = this.translate.instant('generic-error.500.summary');
        welcomMsg.detail = this.translate.instant('generic-error.500.detail');
        welcomMsg.severity = 'error';
        this.msgs.push(welcomMsg);
      }
    });
  }
}