/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecruitTestModule } from '../../../test.module';
import { DeliveryComponent } from '../../../../../../main/webapp/app/entities/delivery/delivery.component';
import { DeliveryService } from '../../../../../../main/webapp/app/entities/delivery/delivery.service';
import { Delivery } from '../../../../../../main/webapp/app/entities/delivery/delivery.model';

describe('Component Tests', () => {

    describe('Delivery Management Component', () => {
        let comp: DeliveryComponent;
        let fixture: ComponentFixture<DeliveryComponent>;
        let service: DeliveryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [DeliveryComponent],
                providers: [
                    DeliveryService
                ]
            })
            .overrideTemplate(DeliveryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeliveryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Delivery(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.deliveries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
