import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IJournalS } from 'app/shared/model/journal-s.model';
import { JournalSService } from './journal-s.service';

@Component({
    selector: 'jhi-journal-s-update',
    templateUrl: './journal-s-update.component.html'
})
export class JournalSUpdateComponent implements OnInit {
    private _journalS: IJournalS;
    isSaving: boolean;
    creationDateDp: any;

    constructor(private journalSService: JournalSService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ journalS }) => {
            this.journalS = journalS;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.journalS.id !== undefined) {
            this.subscribeToSaveResponse(this.journalSService.update(this.journalS));
        } else {
            this.subscribeToSaveResponse(this.journalSService.create(this.journalS));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IJournalS>>) {
        result.subscribe((res: HttpResponse<IJournalS>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get journalS() {
        return this._journalS;
    }

    set journalS(journalS: IJournalS) {
        this._journalS = journalS;
    }
}
