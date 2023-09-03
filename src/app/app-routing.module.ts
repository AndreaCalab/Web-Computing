import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VisualizzaImmobiliComponent } from './visualizza-immobili/visualizza-immobili.component';
import { AddAnnuncioComponent } from './add-annuncio/add-annuncio.component';
import { UpdateAnnunciComponent } from './update-annunci/update-annunci.component';
import { EnterReviewComponent } from './enter-review/enter-review.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { SearchImmobiliComponent } from './search-immobili/search-immobili.component';
import { AstaAnnuncioComponent } from './asta-annuncio/asta-annuncio.component';
import { VisualizzaAsteComponent } from './visualizza-aste/visualizza-aste.component';
import { ModuloComponent } from './modulo/modulo.component';
import { ChatVenditoreComponent } from './chat-venditore/chat-venditore.component';


const routes: Routes = [
  {path: 'visualizzaimmobili', component: VisualizzaImmobiliComponent},
  {path:'addannuncio',component: AddAnnuncioComponent},
  {path:'updateannucio',component: UpdateAnnunciComponent},
  {path:'enterreview',component:EnterReviewComponent},
  {path:'adminusers',component:AdminUsersComponent},
  {path:'searchimmobili',component:SearchImmobiliComponent},
  {path:'astaannuncio',component:AstaAnnuncioComponent},
  {path:'visualizzaaste',component:VisualizzaAsteComponent},
  {path:'modulo',component:ModuloComponent},
  {path:'chatvenditore',component:ChatVenditoreComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
