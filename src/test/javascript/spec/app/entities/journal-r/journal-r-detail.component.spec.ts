/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { JournalRDetailComponent } from 'app/entities/journal-r/journal-r-detail.component';
import { JournalR } from 'app/shared/model/journal-r.model';

describe('Component Tests', () => {
    describe('JournalR Management Detail Component', () => {
        let comp: JournalRDetailComponent;
        let fixture: ComponentFixture<JournalRDetailComponent>;
        const route = ({ data: of({ journalR: new JournalR(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalRDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JournalRDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalRDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.journalR).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
