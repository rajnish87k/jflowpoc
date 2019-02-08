/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { JournalRUpdateComponent } from 'app/entities/journal-r/journal-r-update.component';
import { JournalRService } from 'app/entities/journal-r/journal-r.service';
import { JournalR } from 'app/shared/model/journal-r.model';

describe('Component Tests', () => {
    describe('JournalR Management Update Component', () => {
        let comp: JournalRUpdateComponent;
        let fixture: ComponentFixture<JournalRUpdateComponent>;
        let service: JournalRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalRUpdateComponent]
            })
                .overrideTemplate(JournalRUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JournalRUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalRService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JournalR(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.journalR = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JournalR();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.journalR = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
