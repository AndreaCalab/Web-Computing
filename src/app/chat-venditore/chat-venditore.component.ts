import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Messaggio } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chat-venditore',
  templateUrl: './chat-venditore.component.html',
  styleUrls: ['./chat-venditore.component.css']
})
export class ChatVenditoreComponent implements OnInit{
sessionId:string="";
messaggi:Observable<Messaggio[]>=new Observable<Messaggio[]>;
mess:Messaggio[]=[];
opzioni:string[]=['Rispondi','Rispondi e conferma vendita'];

rispondi(){
  let id=(<HTMLSelectElement>document.getElementById("id")).value;
  let op=(<HTMLSelectElement>document.getElementById("comando")).value;
  let text=(<HTMLTextAreaElement>document.getElementById("answer")).value;
  let risp=id+";"+op+";"+text;
  this.messaggi=this.servizi.rispondiMessaggio(this.sessionId,risp);
  this.messaggi.subscribe(m=>this.mess=m);
  alert("Risposta inviata");
}

setFiltro(m:Messaggio){
  if(m.operazione=='Nessuna')
    this.opzioni=['Rispondi'];
    else
    this.opzioni=['Rispondi','Rispondi e conferma vendita'];
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
        this.messaggi=this.servizi.getMessaggiClienti(sessionId);
        this.messaggi.subscribe(m=>this.mess=m);
      }
    } );
}
}
