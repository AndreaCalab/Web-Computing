import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { Asta, Immobile, Recensione,Utente } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';
import { Observable} from 'rxjs';



@Component({
  selector: 'app-visualizza-immobili',
  templateUrl: './visualizza-immobili.component.html',
  styleUrls: ['./visualizza-immobili.component.css']
})
export class VisualizzaImmobiliComponent implements OnInit{
sessionId:string="";
immobili:Immobile[]=[];
recensioni:Recensione[]=[];
rec:Observable<Recensione[]>=new Observable<Recensione[]>;
immobile:Observable<Immobile[]>=new Observable<Immobile[]>;
opzioni:String[]=[];
imm?:Immobile;
utenti:Observable<String[]>=new Observable<String[]>;
amministratore:string="amministratore;cliente";
tipo:String[]=[];

constructor(
  private servizi: ServerserviceService,
  private route: ActivatedRoute
  ) {
    
  }

  setFiltro(opzione:String){
    this.immobile=this.servizi.setFilter(this.sessionId,opzione as string);
    this.immobile.subscribe(imm=>this.immobili=imm)
  }
  
  eliminaImmobile(){
    var id=(<HTMLSelectElement>document.getElementById("id")).value;
    alert("id:"+id);
      this.servizi.deleteAnnuncio(this.sessionId,id).subscribe(deleted=>{
        if(deleted){
          this.immobili.forEach(function(elem,index,arr){
            if(elem.id===id){
            arr.splice(index,1);
            alert("L'immobile "+id+" è stato eliminato con successo");
            }
          });
        }
      });

  }
  


  ngOnInit(): void {
    var sessionId = this.route.queryParams.subscribe(
      params => {
        var sessionId = params['jsessionid'];
        if (sessionId != null){
          this.sessionId = sessionId;
          this.immobile=this.servizi.getImmobili(sessionId);
          this.rec=this.servizi.getRecensioni(sessionId);
          this.rec.subscribe(recs=>this.recensioni=recs);
          this.immobile.subscribe(imm=>this.immobili=imm);
          this.opzioni=["Prezzo","Metri quadri"];
          this.utenti=this.servizi.getUtenteTipo(sessionId,this.amministratore);
          this.utenti.subscribe(ut=>this.tipo=ut);
        }
      } );
    
    
  }
 

