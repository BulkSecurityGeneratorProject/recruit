/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RecruitTestModule } from '../../../test.module';
import { DeliveryDetailComponent } from '../../../../../../main/webapp/app/entities/delivery/delivery-detail.component';
import { DeliveryService } from '../../../../../../main/webapp/app/entities/delivery/delivery.service';
import { Delivery } from '../../../../../../main/webapp/app/entities/delivery/delivery.model';

describe('Component Tests', () => {

    describe('Delivery Management Detail Component', () => {
        let comp: DeliveryDetailComponent;
        let fixture: ComponentFixture<DeliveryDetailComponent>;
        let service: DeliveryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [DeliveryDetailComponent],
                providers: [
                    DeliveryService
                ]
            })
            .overrideTemplate(DeliveryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeliveryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliveryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Delivery(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.delivery).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
