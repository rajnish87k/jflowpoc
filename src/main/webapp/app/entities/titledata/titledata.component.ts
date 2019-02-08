import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITitledata } from 'app/shared/model/titledata.model';
import { Principal } from 'app/core';
import { TitledataService } from './titledata.service';

@Component({
    selector: 'jhi-titledata',
    templateUrl: './titledata.component.html'
})
export class TitledataComponent implements OnInit, OnDestroy {
    titledata: ITitledata[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private titledataService: TitledataService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.titledataService.query().subscribe(
            (res: HttpResponse<ITitledata[]>) => {
                this.titledata = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTitledata();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITitledata) {
        return item.id;
    }

    registerChangeInTitledata() {
        this.eventSubscriber = this.eventManager.subscribe('titledataListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
