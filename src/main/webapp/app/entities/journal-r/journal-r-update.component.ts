import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IJournalR } from 'app/shared/model/journal-r.model';
import { JournalRService } from './journal-r.service';
import { IJournalS } from 'app/shared/model/journal-s.model';
import { JournalSService } from 'app/entities/journal-s';

@Component({
    selector: 'jhi-journal-r-update',
    templateUrl: './journal-r-update.component.html'
})
export class JournalRUpdateComponent implements OnInit {
    private _journalR: IJournalR;
    isSaving: boolean;

    journals: IJournalS[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private journalRService: JournalRService,
        private journalSService: JournalSService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ journalR }) => {
            this.journalR = journalR;
        });
        this.journalSService.query().subscribe(
            (res: HttpResponse<IJournalS[]>) => {
                this.journals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.journalR.id !== undefined) {
            this.subscribeToSaveResponse(this.journalRService.update(this.journalR));
        } else {
            this.subscribeToSaveResponse(this.journalRService.create(this.journalR));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IJournalR>>) {
        result.subscribe((res: HttpResponse<IJournalR>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackJournalSById(index: number, item: IJournalS) {
        return item.id;
    }
    get journalR() {
        return this._journalR;
    }

    set journalR(journalR: IJournalR) {
        this._journalR = journalR;
    }
}
