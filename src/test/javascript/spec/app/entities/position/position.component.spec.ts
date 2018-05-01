/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecruitTestModule } from '../../../test.module';
import { PositionComponent } from '../../../../../../main/webapp/app/entities/position/position.component';
import { PositionService } from '../../../../../../main/webapp/app/entities/position/position.service';
import { Position } from '../../../../../../main/webapp/app/entities/position/position.model';

describe('Component Tests', () => {

    describe('Position Management Component', () => {
        let comp: PositionComponent;
        let fixture: ComponentFixture<PositionComponent>;
        let service: PositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [PositionComponent],
                providers: [
                    PositionService
                ]
            })
            .overrideTemplate(PositionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PositionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PositionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Position(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.positions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
