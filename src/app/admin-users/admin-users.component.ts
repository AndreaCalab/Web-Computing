import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Utente } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit{
sessionId:string="";
utente:Observable<Utente[]>=new Observable<Utente[]>;
utenti:Utente[]=[];
opzioni:String[]=["Promuovi ad amministratore","Banna","Rimuovi il ban"];
parametri:string="";

constructor(
  private servizi: ServerserviceService,
  private route: ActivatedRoute
  ) {
    
  }
promoteOrBan(){
  var op:string=(<HTMLSelectElement>document.getElementById("opzione")).value;
  var username:string=(<HTMLInputElement>document.getElementById("username")).value;
  this.parametri=username+";"+op;
  this.utente=this.servizi.promoteOrBanUsers(this.sessionId,this.parametri);
  this.utente.subscribe(ut=>this.utenti=ut);
  alert("L'operazione è avvenuta con successo!.Ricaricare la pagina");
}


ngOnInit(): void {
  var sessionId = this.route.queryParams.subscribe(
    params => {
      var sessionId = params['jsessionid'];
      if (sessionId != null){
        this.sessionId = sessionId;
        this.utente=this.servizi.getAdminUsers(sessionId);
        this.utente.subscribe(ut=>this.utenti=ut);
      }
    } );
}
}
