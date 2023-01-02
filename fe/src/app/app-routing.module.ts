import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

export const routes: Routes = [
  {
      path: "init/applicazione",
      title: "Inserimento amministratore",
      loadChildren: () => import ('./components/shared/utenti/utente/utente-componente-module/utente-componente-module.module').then((m)=> m.UtenteComponenteModuleModule)
  },
  {
    path: "login",
    title: "Accedi",
    loadChildren: () => import ('./components/shared/login/login/login.module').then((m)=> m.LoginModule)
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
