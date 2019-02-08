import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITitledata } from 'app/shared/model/titledata.model';
import { TitledataService } from './titledata.service';

@Component({
    selector: 'jhi-titledata-update',
    templateUrl: './titledata-update.component.html'
})
export class TitledataUpdateComponent implements OnInit {
    private _titledata: ITitledata;
    isSaving: boolean;
    creationDateDp: any;

    constructor(private titledataService: TitledataService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ titledata }) => {
            this.titledata = titledata;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.titledata.id !== undefined) {
            this.subscribeToSaveResponse(this.titledataService.update(this.titledata));
        } else {
            this.subscribeToSaveResponse(this.titledataService.create(this.titledata));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITitledata>>) {
        result.subscribe((res: HttpResponse<ITitledata>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get titledata() {
        return this._titledata;
    }

    set titledata(titledata: ITitledata) {
        this._titledata = titledata;
    }
}
