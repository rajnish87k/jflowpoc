import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TagInputModule } from 'ngx-chips';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // this is needed!
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JflowSharedModule } from 'app/shared';
import {
    JournalSComponent,
    JournalSDetailComponent,
    JournalSUpdateComponent,
    JournalSDeletePopupComponent,
    JournalSDeleteDialogComponent,
    journalSRoute,
    journalSPopupRoute
} from './';

const ENTITY_STATES = [...journalSRoute, ...journalSPopupRoute];

@NgModule({
    imports: [
        JflowSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        TagInputModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        JournalSComponent,
        JournalSDetailComponent,
        JournalSUpdateComponent,
        JournalSDeleteDialogComponent,
        JournalSDeletePopupComponent
    ],
    entryComponents: [JournalSComponent, JournalSUpdateComponent, JournalSDeleteDialogComponent, JournalSDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JflowJournalSModule {}
