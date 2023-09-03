import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { VisualizzaImmobiliComponent } from './visualizza-immobili/visualizza-immobili.component';
import { RouterTestingModule } from '@angular/router/testing';
import { AddAnnuncioComponent } from './add-annuncio/add-annuncio.component';
import { UpdateAnnunciComponent } from './update-annunci/update-annunci.component';
import { EnterReviewComponent } from './enter-review/enter-review.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { AstaAnnuncioComponent } from './asta-annuncio/asta-annuncio.component';
import { SearchImmobiliComponent } from './search-immobili/search-immobili.component';
import { VisualizzaAsteComponent } from './visualizza-aste/visualizza-aste.component';
import { ModuloComponent } from './modulo/modulo.component';
import { ChatVenditoreComponent } from './chat-venditore/chat-venditore.component';
import { FacebookModule } from 'ngx-facebook';

@NgModule({
  declarations: [
    AppComponent,
    VisualizzaImmobiliComponent,
    AddAnnuncioComponent,
    UpdateAnnunciComponent,
    EnterReviewComponent,
    AdminUsersComponent,
    AstaAnnuncioComponent,
    SearchImmobiliComponent,
    VisualizzaAsteComponent,
    ModuloComponent,
    ChatVenditoreComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot([]),
    HttpClientModule,
    FormsModule,
    FacebookModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  

}
