import { Component, OnInit } from '@angular/core';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';
import { Immobile } from '../Recensione';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-search-immobili',
  templateUrl: './search-immobili.component.html',
  styleUrls: ['./search-immobili.component.css']
})
export class SearchImmobiliComponent implements OnInit{
 sessionId:string="";
 immobile:Observable<Immobile[]>=new Observable<Immobile[]>;
  Immobili:Immobile[]=[];
  
  filtro(){
    let tipo=(<HTMLSelectElement>document.getElementById("tipo")).value;
    let op=(<HTMLSelectElement>document.getElementById("operazione")).value;
    let filtro=tipo+" "+op;
    this.immobile=this.servizi.getImmobiliSearchList(this.sessionId,filtro);
    this.immobile.subscribe(imm=>this.Immobili=imm);
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
          this.immobile=this.servizi.getImmobili(sessionId);
          this.immobile.subscribe(imm=>this.Immobili=imm);
        }
      } );
  }
 

}
