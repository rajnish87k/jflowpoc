/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JflowTestModule } from '../../../test.module';
import { JournalRDeleteDialogComponent } from 'app/entities/journal-r/journal-r-delete-dialog.component';
import { JournalRService } from 'app/entities/journal-r/journal-r.service';

describe('Component Tests', () => {
    describe('JournalR Management Delete Component', () => {
        let comp: JournalRDeleteDialogComponent;
        let fixture: ComponentFixture<JournalRDeleteDialogComponent>;
        let service: JournalRService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalRDeleteDialogComponent]
            })
                .overrideTemplate(JournalRDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalRDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalRService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
