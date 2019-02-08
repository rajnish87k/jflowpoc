import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGscontent } from 'app/shared/model/gscontent.model';
import { GscontentService } from './gscontent.service';

@Component({
    selector: 'jhi-gscontent-delete-dialog',
    templateUrl: './gscontent-delete-dialog.component.html'
})
export class GscontentDeleteDialogComponent {
    gscontent: IGscontent;

    constructor(private gscontentService: GscontentService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gscontentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'gscontentListModification',
                content: 'Deleted an gscontent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gscontent-delete-popup',
    template: ''
})
export class GscontentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gscontent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GscontentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.gscontent = gscontent;
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
