import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Asta } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-visualizza-aste',
  templateUrl: './visualizza-aste.component.html',
  styleUrls: ['./visualizza-aste.component.css']
})
export class VisualizzaAsteComponent implements OnInit{
  sessionId:string="";
  aste:Observable<Asta[]>=new Observable<Asta[]>;
  asta:Asta[]=[];
  asteUt:Observable<Asta[]>=new Observable<Asta[]>;
  astaUt:Asta[]=[];

  piazzaOfferta(){
    let off=(<HTMLInputElement>document.getElementById("offerta")).value;
    let id=(<HTMLSelectElement>document.getElementById("codice")).value;
    let offerta:string=id+","+off;
    this.aste=this.servizi.updateOfferta(this.sessionId,offerta);
    this.aste.subscribe(as=>this.asta=as);
  }
  terminaAsta(){
    let code=(<HTMLSelectElement>document.getElementById("codice1")).value;
    this.asteUt=this.servizi.terminaAsta(this.sessionId,code);
    this.asteUt.subscribe(as=>this.astaUt=as);
    alert("L'asta è stata terminata con successo.Si prega di ricaricare la pagina");
  }


  constructor(
    private servizi: ServerserviceService,
    private route: ActivatedRoute
    ) {
      
    }


  ngOnInit(): void {
    var sessionId = this.route.queryParams.subscribe(
      params => {
        var sessionId = params['jsessionid'];
        if (sessionId != null){
          this.sessionId = sessionId;
          this.aste=this.servizi.getAstaUtentiList(sessionId);
          this.aste.subscribe(as=>this.asta=as);
          this.asteUt=this.servizi.getAstaUtenteList(sessionId);
          this.asteUt.subscribe(as=>this.astaUt=as);
          } 
      } );
  }
}

