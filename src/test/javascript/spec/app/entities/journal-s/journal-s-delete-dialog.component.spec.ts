/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JflowTestModule } from '../../../test.module';
import { JournalSDeleteDialogComponent } from 'app/entities/journal-s/journal-s-delete-dialog.component';
import { JournalSService } from 'app/entities/journal-s/journal-s.service';

describe('Component Tests', () => {
    describe('JournalS Management Delete Component', () => {
        let comp: JournalSDeleteDialogComponent;
        let fixture: ComponentFixture<JournalSDeleteDialogComponent>;
        let service: JournalSService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalSDeleteDialogComponent]
            })
                .overrideTemplate(JournalSDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalSDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalSService);
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
