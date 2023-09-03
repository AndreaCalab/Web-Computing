import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Asta, Immobile } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-asta-annuncio',
  templateUrl: './asta-annuncio.component.html',
  styleUrls: ['./asta-annuncio.component.css']
})
export class AstaAnnuncioComponent implements OnInit{
  sessionId:string="";
  aste:Observable<Asta[]>=new Observable<Asta[]>;
  asta:Asta[]=[];
  a?:Asta;
  immobili:Observable<Immobile[]>=new Observable<Immobile[]>;
  immobile:Immobile[]=[];
  

  addAsta(){
      let code=(<HTMLInputElement>document.getElementById("codice")).value;
      let imm=(<HTMLSelectElement>document.getElementById("immobile")).value;
      let prezzo=(<HTMLInputElement>document.getElementById("prezzo_base")).value as unknown as number;
      let date=(<HTMLInputElement>document.getElementById("data_scadenza")).value as string;
      let num=0;
      let asta:Asta=<Asta>this.a;
      asta=new Asta(imm,code,prezzo,num as unknown as number,"in corso","",date);
      this.a=new Asta(imm,code,prezzo,num as unknown as number,"in corso","",date);
      this.aste=this.servizi.addAsta(this.sessionId,asta);
      this.aste.subscribe(as=>this.asta=as);
      alert("L'operazione è avvenuta con successo.Si prega di ricaricare la pagina");
   
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
          this.immobili=this.servizi.getImmobiliUtente(sessionId);
          this.immobili.subscribe(imm=>this.immobile=imm);
        }
      } );
  }

}
