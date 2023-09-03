import { Component, OnInit } from '@angular/core';
import { Immobile } from '../Recensione';
import { Observable } from 'rxjs';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';
import { VisualizzaImmobiliComponent } from '../visualizza-immobili/visualizza-immobili.component';

@Component({
  selector: 'app-update-annunci',
  templateUrl: './update-annunci.component.html',
  styleUrls: ['./update-annunci.component.css']
})
export class UpdateAnnunciComponent implements OnInit{
  immobili:Immobile[]=[];
  imm:Observable<Immobile[]>=new Observable<Immobile[]>;
  sessionId:string="";
  immobile?:Immobile;
  categoria:string[]=[];
  tipo:String[]=[];
  utenti:Observable<String[]>=new Observable<String[]>;
  admin:string="amministratore;cliente";
  immobili_ut:Immobile[]=[];
  imm_ut:Observable<Immobile[]>=new Observable<Immobile[]>;
  
  constructor(
    private servizi: ServerserviceService,
    private route: ActivatedRoute
    ) {
      
    }

    delete(){
      var id=(<HTMLSelectElement>document.getElementById("id")).value;
      this.servizi.deleteAnnuncio(this.sessionId,id).subscribe(deleted=>{
        if(deleted){
          this.immobili.forEach(function(elem,index,arr){
            if(elem.id===id)
            arr.splice(index,1);
          alert("L'immobile "+id+" è stato eliminato con successo.Si prega di ricaricare la pagina");
          });
        }
      });
    }

    Update(){
      let immo:Immobile=<Immobile>this.immobile;
      let id2=(<HTMLSelectElement>document.getElementById("id2")).value;
      let categoria=(<HTMLSelectElement>document.getElementById("categoria")).value;
      let descrizione=(<HTMLInputElement>document.getElementById("descrizione")).value;
      let prezzo=(<HTMLInputElement>document.getElementById("prezzo")).value as unknown as number;
      let metri_quadri=(<HTMLInputElement>document.getElementById("metri_quadri")).value as unknown as number;
      let posizione=(<HTMLInputElement>document.getElementById("posizione")).value;
      immo=new Immobile(categoria,descrizione,prezzo,metri_quadri,posizione,id2,"");
      this.imm=this.servizi.updateAnnuncio(this.sessionId,immo);
      this.imm.subscribe(i=>this.immobili=i);
      if(immo!=null)
      alert("Operazione avvenuta con successo!")
    }

  ngOnInit(): void {
    var sessionId = this.route.queryParams.subscribe(
      params => {
        var sessionId = params['jsessionid'];
        if (sessionId != null){
          this.sessionId = sessionId;
          this.imm=this.servizi.getImmobiliUtente(sessionId);
          this.imm.subscribe(imm=>this.immobili=imm);
          this.categoria=["appartamento","villa"];
          this.utenti=this.servizi.getUtenteTipo(sessionId,this.admin);
          this.utenti.subscribe(ut=>this.tipo=ut);
            this.imm_ut=this.servizi.getImmobiliUtenti(sessionId);
            this.imm_ut.subscribe(imm=>this.immobili_ut=imm);
          

        }
      } );
  }

}
