/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { JournalSDetailComponent } from 'app/entities/journal-s/journal-s-detail.component';
import { JournalS } from 'app/shared/model/journal-s.model';

describe('Component Tests', () => {
    describe('JournalS Management Detail Component', () => {
        let comp: JournalSDetailComponent;
        let fixture: ComponentFixture<JournalSDetailComponent>;
        const route = ({ data: of({ journalS: new JournalS(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [JournalSDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JournalSDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalSDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.journalS).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
