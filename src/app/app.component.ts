import { Component, OnInit } from '@angular/core';
import { ServerserviceService } from './serverservice.service';
import { Utente } from './Recensione';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Servizi';
  sessionId: string = "";
  isLogged: Boolean = false;
  utenti:Observable<String[]>=new Observable<String[]>;
  utente?:Utente;
  tipo:String[]=[];
  amministratore:string="amministratore;cliente";

  ngOnInit(): void {
    //Parametri
    const urlParams = new URLSearchParams(window.location.search);
    var sessionId = urlParams.get("jsessionid");
    if (sessionId){
      this.servizi.checkLogin(sessionId).subscribe(ok => {
        this.isLogged = ok;
        if (ok){
          if (sessionId != null){
            this.sessionId = sessionId;
            this.utenti=this.servizi.getUtenteTipo(sessionId,this.amministratore);
            if(this.utenti)
            this.utenti.subscribe(ut=>this.tipo=ut);
            else
            alert("Impossibile avere il tipo");
          }
        }
      });
    }
  }
constructor(private servizi: ServerserviceService){}
}
