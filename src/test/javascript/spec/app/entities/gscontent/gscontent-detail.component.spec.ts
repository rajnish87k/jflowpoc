/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { GscontentDetailComponent } from 'app/entities/gscontent/gscontent-detail.component';
import { Gscontent } from 'app/shared/model/gscontent.model';

describe('Component Tests', () => {
    describe('Gscontent Management Detail Component', () => {
        let comp: GscontentDetailComponent;
        let fixture: ComponentFixture<GscontentDetailComponent>;
        const route = ({ data: of({ gscontent: new Gscontent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [GscontentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GscontentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GscontentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gscontent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
