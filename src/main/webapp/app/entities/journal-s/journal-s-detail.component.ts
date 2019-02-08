import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournalS } from 'app/shared/model/journal-s.model';

@Component({
    selector: 'jhi-journal-s-detail',
    templateUrl: './journal-s-detail.component.html'
})
export class JournalSDetailComponent implements OnInit {
    journalS: IJournalS;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalS }) => {
            this.journalS = journalS;
        });
    }

    previousState() {
        window.history.back();
    }
}
