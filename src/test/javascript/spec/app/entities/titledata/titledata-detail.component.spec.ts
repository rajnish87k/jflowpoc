/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JflowTestModule } from '../../../test.module';
import { TitledataDetailComponent } from 'app/entities/titledata/titledata-detail.component';
import { Titledata } from 'app/shared/model/titledata.model';

describe('Component Tests', () => {
    describe('Titledata Management Detail Component', () => {
        let comp: TitledataDetailComponent;
        let fixture: ComponentFixture<TitledataDetailComponent>;
        const route = ({ data: of({ titledata: new Titledata(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JflowTestModule],
                declarations: [TitledataDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TitledataDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TitledataDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.titledata).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
