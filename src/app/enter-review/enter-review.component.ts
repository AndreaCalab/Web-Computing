import { Component, OnInit } from '@angular/core';
import { Immobile, Recensione } from '../Recensione';
import { ServerserviceService } from '../serverservice.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, sequenceEqual } from 'rxjs';

@Component({
  selector: 'app-enter-review',
  templateUrl: './enter-review.component.html',
  styleUrls: ['./enter-review.component.css']
})
export class EnterReviewComponent implements OnInit{
immobili:Immobile[]=[];
recensioni:Recensione[]=[];
r:Recensione[]=[];
sessionId:string="";
recensione?:Recensione;
rec:Observable<Recensione[]>=new Observable<Recensione[]>;

constructor(
  private servizi: ServerserviceService,
  private route: ActivatedRoute
  ) {
    
  }

  addReview(){
    var imm=(<HTMLSelectElement>document.getElementById("immobile")).value;
    var rec=(<HTMLInputElement>document.getElementById("recensione")).value;
    this.recensione=new Recensione("","",imm,rec);
    this.rec=this.servizi.addRecensione(this.sessionId,this.recensione);
    this.rec.subscribe(recs=>this.recensioni=recs);
    
  }

ngOnInit(): void {
  var sessionId = this.route.queryParams.subscribe(
    params => {
      var sessionId = params['jsessionid'];
      if (sessionId != null){
        this.sessionId = sessionId;
        var obs:Observable<Immobile[]>=this.servizi.getImmobiliUtenti(sessionId);
        obs.subscribe(imm=>this.immobili=imm);
        this.rec=this.servizi.getRecensioni(sessionId);
        this.rec.subscribe(recs=>this.recensioni=recs);
        this.rec.subscribe(recs=>this.r=recs);
      }
    } );
}
  
}


