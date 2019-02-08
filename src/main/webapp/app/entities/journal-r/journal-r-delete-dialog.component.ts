import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJournalR } from 'app/shared/model/journal-r.model';
import { JournalRService } from './journal-r.service';

@Component({
    selector: 'jhi-journal-r-delete-dialog',
    templateUrl: './journal-r-delete-dialog.component.html'
})
export class JournalRDeleteDialogComponent {
    journalR: IJournalR;

    constructor(private journalRService: JournalRService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.journalRService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'journalRListModification',
                content: 'Deleted an journalR'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-journal-r-delete-popup',
    template: ''
})
export class JournalRDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalR }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JournalRDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.journalR = journalR;
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
