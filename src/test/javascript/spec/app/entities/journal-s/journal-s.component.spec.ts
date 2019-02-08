/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JflowTestModule } from '../../../test.module';
import { JournalSComponent } from 'app/entities/journal-s/journal-s.component';
import { JournalSService } from 'app/entities/journal-s/journal-s.service';
import { JournalS } from 'app/shared/model/journal-s.model';

describe('Component Tests', () => {
    describe('JournalS Management Component', () => {
        let comp: JournalSComponent;
        let fixture: ComponentFixture<JournalSComponent>;
        let service: JournalSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalSComponent],
                providers: []
            })
                .overrideTemplate(JournalSComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JournalSComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalSService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new JournalS(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.journalS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
