import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JflowSharedModule } from 'app/shared';
import {
    GscontentComponent,
    GscontentDetailComponent,
    GscontentUpdateComponent,
    GscontentDeletePopupComponent,
    GscontentDeleteDialogComponent,
    gscontentRoute,
    gscontentPopupRoute
} from './';

const ENTITY_STATES = [...gscontentRoute, ...gscontentPopupRoute];

@NgModule({
    imports: [JflowSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GscontentComponent,
        GscontentDetailComponent,
        GscontentUpdateComponent,
        GscontentDeleteDialogComponent,
        GscontentDeletePopupComponent
    ],
    entryComponents: [GscontentComponent, GscontentUpdateComponent, GscontentDeleteDialogComponent, GscontentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JflowGscontentModule {}
