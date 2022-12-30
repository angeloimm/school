import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Utente } from 'src/app/models/utente';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.scss']
})
export class UtenteComponent implements OnInit {
  utente!: Utente;  
  uploadedFiles!: any[];
  multipleUpload: boolean = true;
  constructor(private route: ActivatedRoute){}
  ngOnInit(): void {
    this.utente.attivo = true;
    if( this.route.snapshot.queryParamMap.get('tipoUtente') != null ){
    
      this.utente.tipoUtente = this.route.snapshot.queryParamMap.get('tipoUtente')!;
    }
  }
  onUpload(event){
    for(let file of event.files){
      this.uploadedFiles.push(file);
    }
  }
}
