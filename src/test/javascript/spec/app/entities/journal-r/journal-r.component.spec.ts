/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JflowTestModule } from '../../../test.module';
import { JournalRComponent } from 'app/entities/journal-r/journal-r.component';
import { JournalRService } from 'app/entities/journal-r/journal-r.service';
import { JournalR } from 'app/shared/model/journal-r.model';

describe('Component Tests', () => {
    describe('JournalR Management Component', () => {
        let comp: JournalRComponent;
        let fixture: ComponentFixture<JournalRComponent>;
        let service: JournalRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalRComponent],
                providers: []
            })
                .overrideTemplate(JournalRComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JournalRComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalRService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new JournalR(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.journalRS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
