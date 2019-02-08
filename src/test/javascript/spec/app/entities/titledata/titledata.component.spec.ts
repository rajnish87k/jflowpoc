/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JflowTestModule } from '../../../test.module';
import { TitledataComponent } from 'app/entities/titledata/titledata.component';
import { TitledataService } from 'app/entities/titledata/titledata.service';
import { Titledata } from 'app/shared/model/titledata.model';

describe('Component Tests', () => {
    describe('Titledata Management Component', () => {
        let comp: TitledataComponent;
        let fixture: ComponentFixture<TitledataComponent>;
        let service: TitledataService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [TitledataComponent],
                providers: []
            })
                .overrideTemplate(TitledataComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TitledataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TitledataService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Titledata(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.titledata[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
