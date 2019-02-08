import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JflowSharedModule } from 'app/shared';
import {
    JournalRComponent,
    JournalRDetailComponent,
    JournalRUpdateComponent,
    JournalRDeletePopupComponent,
    JournalRDeleteDialogComponent,
    journalRRoute,
    journalRPopupRoute
} from './';

const ENTITY_STATES = [...journalRRoute, ...journalRPopupRoute];

@NgModule({
    imports: [JflowSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JournalRComponent,
        JournalRDetailComponent,
        JournalRUpdateComponent,
        JournalRDeleteDialogComponent,
        JournalRDeletePopupComponent
    ],
    entryComponents: [JournalRComponent, JournalRUpdateComponent, JournalRDeleteDialogComponent, JournalRDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JflowJournalRModule {}
