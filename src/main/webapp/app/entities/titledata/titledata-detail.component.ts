import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITitledata } from 'app/shared/model/titledata.model';

@Component({
    selector: 'jhi-titledata-detail',
    templateUrl: './titledata-detail.component.html'
})
export class TitledataDetailComponent implements OnInit {
    titledata: ITitledata;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ titledata }) => {
            this.titledata = titledata;
        });
    }

    previousState() {
        window.history.back();
    }
}
