import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGscontent } from 'app/shared/model/gscontent.model';

@Component({
    selector: 'jhi-gscontent-detail',
    templateUrl: './gscontent-detail.component.html'
})
export class GscontentDetailComponent implements OnInit {
    gscontent: IGscontent;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gscontent }) => {
            this.gscontent = gscontent;
        });
    }

    previousState() {
        window.history.back();
    }
}
