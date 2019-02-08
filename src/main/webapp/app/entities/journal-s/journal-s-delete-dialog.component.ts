import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJournalS } from 'app/shared/model/journal-s.model';
import { JournalSService } from './journal-s.service';

@Component({
    selector: 'jhi-journal-s-delete-dialog',
    templateUrl: './journal-s-delete-dialog.component.html'
})
export class JournalSDeleteDialogComponent {
    journalS: IJournalS;

    constructor(private journalSService: JournalSService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.journalSService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'journalSListModification',
                content: 'Deleted an journalS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-journal-s-delete-popup',
    template: ''
})
export class JournalSDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalS }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JournalSDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.journalS = journalS;
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
