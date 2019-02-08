/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JflowTestModule } from '../../../test.module';
import { TitledataDeleteDialogComponent } from 'app/entities/titledata/titledata-delete-dialog.component';
import { TitledataService } from 'app/entities/titledata/titledata.service';

describe('Component Tests', () => {
    describe('Titledata Management Delete Component', () => {
        let comp: TitledataDeleteDialogComponent;
        let fixture: ComponentFixture<TitledataDeleteDialogComponent>;
        let service: TitledataService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [TitledataDeleteDialogComponent]
            })
                .overrideTemplate(TitledataDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TitledataDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TitledataService);
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
