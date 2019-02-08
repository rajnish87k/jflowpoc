/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { JournalSUpdateComponent } from 'app/entities/journal-s/journal-s-update.component';
import { JournalSService } from 'app/entities/journal-s/journal-s.service';
import { JournalS } from 'app/shared/model/journal-s.model';

describe('Component Tests', () => {
    describe('JournalS Management Update Component', () => {
        let comp: JournalSUpdateComponent;
        let fixture: ComponentFixture<JournalSUpdateComponent>;
        let service: JournalSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalSUpdateComponent]
            })
                .overrideTemplate(JournalSUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JournalSUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalSService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JournalS(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.journalS = entity;
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
                    const entity = new JournalS();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.journalS = entity;
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
