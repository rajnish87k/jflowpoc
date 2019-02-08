import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJournalS } from 'app/shared/model/journal-s.model';
import { Principal } from 'app/core';
import { JournalSService } from './journal-s.service';

@Component({
    selector: 'jhi-journal-s',
    templateUrl: './journal-s.component.html'
})
export class JournalSComponent implements OnInit, OnDestroy {
    journalS: IJournalS[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private journalSService: JournalSService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.journalSService.query().subscribe(
            (res: HttpResponse<IJournalS[]>) => {
                this.journalS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJournalS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJournalS) {
        return item.id;
    }

    registerChangeInJournalS() {
        this.eventSubscriber = this.eventManager.subscribe('journalSListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
