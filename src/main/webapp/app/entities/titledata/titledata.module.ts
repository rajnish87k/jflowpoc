import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JflowSharedModule } from 'app/shared';
import {
    TitledataComponent,
    TitledataDetailComponent,
    TitledataUpdateComponent,
    TitledataDeletePopupComponent,
    TitledataDeleteDialogComponent,
    titledataRoute,
    titledataPopupRoute
} from './';

const ENTITY_STATES = [...titledataRoute, ...titledataPopupRoute];

@NgModule({
    imports: [JflowSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TitledataComponent,
        TitledataDetailComponent,
        TitledataUpdateComponent,
        TitledataDeleteDialogComponent,
        TitledataDeletePopupComponent
    ],
    entryComponents: [TitledataComponent, TitledataUpdateComponent, TitledataDeleteDialogComponent, TitledataDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JflowTitledataModule {}
