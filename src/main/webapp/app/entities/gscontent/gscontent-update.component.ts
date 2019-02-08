import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGscontent } from 'app/shared/model/gscontent.model';
import { GscontentService } from './gscontent.service';

@Component({
    selector: 'jhi-gscontent-update',
    templateUrl: './gscontent-update.component.html'
})
export class GscontentUpdateComponent implements OnInit {
    private _gscontent: IGscontent;
    isSaving: boolean;
    creationDateDp: any;

    constructor(private gscontentService: GscontentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gscontent }) => {
            this.gscontent = gscontent;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gscontent.id !== undefined) {
            this.subscribeToSaveResponse(this.gscontentService.update(this.gscontent));
        } else {
            this.subscribeToSaveResponse(this.gscontentService.create(this.gscontent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGscontent>>) {
        result.subscribe((res: HttpResponse<IGscontent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get gscontent() {
        return this._gscontent;
    }

    set gscontent(gscontent: IGscontent) {
        this._gscontent = gscontent;
    }
}
