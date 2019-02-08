/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JflowTestModule } from '../../../test.module';
import { GscontentComponent } from 'app/entities/gscontent/gscontent.component';
import { GscontentService } from 'app/entities/gscontent/gscontent.service';
import { Gscontent } from 'app/shared/model/gscontent.model';

describe('Component Tests', () => {
    describe('Gscontent Management Component', () => {
        let comp: GscontentComponent;
        let fixture: ComponentFixture<GscontentComponent>;
        let service: GscontentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [GscontentComponent],
                providers: []
            })
                .overrideTemplate(GscontentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GscontentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GscontentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Gscontent(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.gscontents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
