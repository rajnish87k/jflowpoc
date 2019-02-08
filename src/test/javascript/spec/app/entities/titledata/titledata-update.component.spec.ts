/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { TitledataUpdateComponent } from 'app/entities/titledata/titledata-update.component';
import { TitledataService } from 'app/entities/titledata/titledata.service';
import { Titledata } from 'app/shared/model/titledata.model';

describe('Component Tests', () => {
    describe('Titledata Management Update Component', () => {
        let comp: TitledataUpdateComponent;
        let fixture: ComponentFixture<TitledataUpdateComponent>;
        let service: TitledataService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [TitledataUpdateComponent]
            })
                .overrideTemplate(TitledataUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TitledataUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TitledataService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Titledata(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.titledata = entity;
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
                    const entity = new Titledata();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.titledata = entity;
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
