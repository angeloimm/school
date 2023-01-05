import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminGuard } from './guards/application-guard';
import { ROUTE_PATH } from './shared/constants';

export const routes: Routes = [
  {
      path: ROUTE_PATH.APP_INIT_ROUTE,
      title: "Inserimento amministratore",
      loadChildren: () => import ('./components/shared/utenti/utente/utente-componente-module/utente-componente-module.module').then((m)=> m.UtenteComponenteModuleModule)
  },
  {
    path: ROUTE_PATH.APP_LOGIN_ROUTE,
    title: "Accedi",
    loadChildren: () => import ('./components/shared/login/login/login.module').then((m)=> m.LoginModule)
  },
  {
    path: ROUTE_PATH.APP_GENERIC_ERROR_ROUTE,
    title: "Errore Generico",
    loadChildren: () => import ('./components/shared/errors/generic-error/generic-error.module').then((m)=> m.GenericErrorModule)
  },
  {
    path: ROUTE_PATH.APP_HP_AMMINISTRATORE_ROUTE,
    title: "HP Amministrazione",
    canActivate:[AdminGuard],
    loadChildren: () => import ('./components/pages/amministratore/admin-page/admin-page.module').then((m)=> m.AdminPageModule)
  },
  {
    path: ROUTE_PATH.APP_HP_UTENTE_ROUTE,
    title: "Gestione utenti",
    canActivate:[AdminGuard],
    loadChildren: () => import ('./components/pages/utente/utente-page/utente-page.module').then((m)=> m.UtentePageModule)
  },
  {
    path: ROUTE_PATH.APP_ADD_UTENTE_ROUTE,
    title: "Inserimento utente",
    canActivate:[AdminGuard],
    loadChildren: () => import ('./components/shared/utenti/utente/utente-componente-module/utente-componente-module.module').then((m)=> m.UtenteComponenteModuleModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
