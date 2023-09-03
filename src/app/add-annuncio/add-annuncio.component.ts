import { Component, OnInit } from '@angular/core';
import { Immobile, Recensione,Utente } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as fs from 'fs';
import * as path from 'path';
@Component({
  selector: 'app-add-annuncio',
  templateUrl: './add-annuncio.component.html',
  styleUrls: ['./add-annuncio.component.css']
})
export class AddAnnuncioComponent implements OnInit{
  cat:String[]=[];
  isLogged:Boolean=false;
  sessionId:string="";
  immobile:Immobile[]=[];
  immobili:Observable<Immobile[]>=new Observable<Immobile[]>;
  imm?:Immobile;
  constructor(private servizi: ServerserviceService,private route: ActivatedRoute){}
  
  
  addAnnuncio(){
    let imm:Immobile=<Immobile>this.imm;
    let categoria=(<HTMLSelectElement>document.getElementById("categoria")).value;
    let descrizione=(<HTMLInputElement>document.getElementById("descrizione")).value;
    let prezzo=(<HTMLInputElement>document.getElementById("prezzo")).value as unknown as number;
    let metri_quadri=(<HTMLInputElement>document.getElementById("metri_quadri")).value as unknown as number;
    let posizione=(<HTMLInputElement>document.getElementById("posizione")).value;
    imm=new Immobile(categoria,descrizione,prezzo,metri_quadri,posizione,"","");
    this.immobili=this.servizi.addAnnuncio(this.sessionId,imm);
    this.immobili.subscribe(imm=>this.immobile=imm);
  }
  
  ngOnInit(): void {
    var sessionId = this.route.queryParams.subscribe(
      params => {
        var sessionId = params['jsessionid'];
        if (sessionId != null){
          this.sessionId = sessionId;
          this.immobili=this.servizi.getImmobili(sessionId);
          this.immobili.subscribe(imm=>this.immobile=imm);
          this.cat=["appartamento","villa"];
        }
      } );
  }

}
