import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournalR } from 'app/shared/model/journal-r.model';

@Component({
    selector: 'jhi-journal-r-detail',
    templateUrl: './journal-r-detail.component.html'
})
export class JournalRDetailComponent implements OnInit {
    journalR: IJournalR;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalR }) => {
            this.journalR = journalR;
        });
    }

    previousState() {
        window.history.back();
    }
}
