import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.CoopcycleClientModule),
      },
      {
        path: 'coursier',
        loadChildren: () => import('./coursier/coursier.module').then(m => m.CoopcycleCoursierModule),
      },
      {
        path: 'commercant',
        loadChildren: () => import('./commercant/commercant.module').then(m => m.CoopcycleCommercantModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CoopcycleEntityModule {}
