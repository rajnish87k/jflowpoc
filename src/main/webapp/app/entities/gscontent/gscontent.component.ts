import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGscontent } from 'app/shared/model/gscontent.model';
import { Principal } from 'app/core';
import { GscontentService } from './gscontent.service';

@Component({
    selector: 'jhi-gscontent',
    templateUrl: './gscontent.component.html'
})
export class GscontentComponent implements OnInit, OnDestroy {
    gscontents: IGscontent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gscontentService: GscontentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.gscontentService.query().subscribe(
            (res: HttpResponse<IGscontent[]>) => {
                this.gscontents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGscontents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGscontent) {
        return item.id;
    }

    registerChangeInGscontents() {
        this.eventSubscriber = this.eventManager.subscribe('gscontentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
