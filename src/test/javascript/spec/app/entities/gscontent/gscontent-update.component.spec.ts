/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { GscontentUpdateComponent } from 'app/entities/gscontent/gscontent-update.component';
import { GscontentService } from 'app/entities/gscontent/gscontent.service';
import { Gscontent } from 'app/shared/model/gscontent.model';

describe('Component Tests', () => {
    describe('Gscontent Management Update Component', () => {
        let comp: GscontentUpdateComponent;
        let fixture: ComponentFixture<GscontentUpdateComponent>;
        let service: GscontentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [GscontentUpdateComponent]
            })
                .overrideTemplate(GscontentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GscontentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GscontentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Gscontent(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gscontent = entity;
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
                    const entity = new Gscontent();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gscontent = entity;
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
