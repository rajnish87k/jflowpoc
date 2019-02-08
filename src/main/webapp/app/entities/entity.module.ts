import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JflowJournalSModule } from './journal-s/journal-s.module';
import { JflowJournalRModule } from './journal-r/journal-r.module';
import { JflowTitledataModule } from './titledata/titledata.module';
import { JflowGscontentModule } from './gscontent/gscontent.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JflowJournalSModule,
        JflowJournalRModule,
        JflowTitledataModule,
        JflowGscontentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JflowEntityModule {}
