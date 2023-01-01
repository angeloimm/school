import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UtenteComponent } from './components/shared/utenti/utente/utente.component';

export const routes: Routes = [
  {
      path: "init/applicazione",
      title: "Inserimento amministratore",
      loadChildren: () => import ('./components/shared/utenti/utente/utente-componente-module/utente-componente-module.module').then((m)=> m.UtenteComponenteModuleModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
