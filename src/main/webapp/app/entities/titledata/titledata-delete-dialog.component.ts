import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITitledata } from 'app/shared/model/titledata.model';
import { TitledataService } from './titledata.service';

@Component({
    selector: 'jhi-titledata-delete-dialog',
    templateUrl: './titledata-delete-dialog.component.html'
})
export class TitledataDeleteDialogComponent {
    titledata: ITitledata;

    constructor(private titledataService: TitledataService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.titledataService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'titledataListModification',
                content: 'Deleted an titledata'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-titledata-delete-popup',
    template: ''
})
export class TitledataDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ titledata }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TitledataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.titledata = titledata;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
