import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Utente } from 'src/app/models/utente';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';

@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.scss']
})
export class UtenteComponent implements OnInit {
  utente: Utente;  
  uploadedFiles: any[] = [];
  multipleUpload: boolean = true;
  constructor(private route: ActivatedRoute, private log:LoggingServiceService){}
  ngOnInit(): void {
    /*this.utente.attivo = true;
    if( this.route.snapshot.queryParamMap.get('tipoUtente') != null ){
    
      this.utente.tipoUtente = this.route.snapshot.queryParamMap.get('tipoUtente')!;

    }
    this.log.printLog("Attivo: "+this.utente.attivo+" tipo utente "+this.utente.tipoUtente);*/
  }
  onUpload(event){
    for(let file of event.files){
      this.uploadedFiles.push(file);
    }
  }
}
