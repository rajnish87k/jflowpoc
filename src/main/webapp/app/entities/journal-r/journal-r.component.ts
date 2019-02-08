import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJournalR } from 'app/shared/model/journal-r.model';
import { Principal } from 'app/core';
import { JournalRService } from './journal-r.service';

@Component({
    selector: 'jhi-journal-r',
    templateUrl: './journal-r.component.html'
})
export class JournalRComponent implements OnInit, OnDestroy {
    journalRS: IJournalR[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private journalRService: JournalRService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.journalRService.query().subscribe(
            (res: HttpResponse<IJournalR[]>) => {
                this.journalRS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJournalRS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJournalR) {
        return item.id;
    }

    registerChangeInJournalRS() {
        this.eventSubscriber = this.eventManager.subscribe('journalRListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
