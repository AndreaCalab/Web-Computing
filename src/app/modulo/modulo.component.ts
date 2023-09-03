import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Immobile, Messaggio, Utente } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-modulo',
  templateUrl: './modulo.component.html',
  styleUrls: ['./modulo.component.css']
})
export class ModuloComponent implements OnInit{
sessionId:string="";
immobili:Observable<Immobile[]>=new Observable<Immobile[]>;
imm:Immobile[]=[];
utenti:Observable<Utente[]>=new Observable<Utente[]>;
ut:Utente[]=[];
messaggi:Observable<Messaggio[]>=new Observable<Messaggio[]>;
mes:Messaggio[]=[];
m?:Messaggio;

constructor(
  private servizi: ServerserviceService,
  private route: ActivatedRoute
  ) {
    
  }

inviaRichiesta(){
  let mes:Messaggio=<Messaggio>this.m;
  let pr=(<HTMLSelectElement>document.getElementById("proprietario")).value;
  let imm=(<HTMLSelectElement>document.getElementById("immobile")).value;
  let sms=(<HTMLInputElement>document.getElementById("messaggio")).value;
  let op=(<HTMLSelectElement>document.getElementById("operazione")).value;
  mes=new Messaggio("","",pr,imm,op,sms);
  this.messaggi=this.servizi.addMessaggio(this.sessionId,mes);
  this.messaggi.subscribe(m=>this.mes=m);
  alert("La richiesta è stata inviata.Si prega di ricaricare la pagina");

}

ngOnInit(): void {
  var sessionId = this.route.queryParams.subscribe(
    params => {
      var sessionId = params['jsessionid'];
      if (sessionId != null){
        this.sessionId = sessionId;
        this.immobili=this.servizi.getImmobiliUtenti(sessionId);
        this.immobili.subscribe(im=>this.imm=im);
        this.utenti=this.servizi.getUtentiList(sessionId);
        this.utenti.subscribe(u=>this.ut=u);
      }
    } );
}

}
