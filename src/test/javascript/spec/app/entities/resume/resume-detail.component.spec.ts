/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RecruitTestModule } from '../../../test.module';
import { ResumeDetailComponent } from '../../../../../../main/webapp/app/entities/resume/resume-detail.component';
import { ResumeService } from '../../../../../../main/webapp/app/entities/resume/resume.service';
import { Resume } from '../../../../../../main/webapp/app/entities/resume/resume.model';

describe('Component Tests', () => {

    describe('Resume Management Detail Component', () => {
        let comp: ResumeDetailComponent;
        let fixture: ComponentFixture<ResumeDetailComponent>;
        let service: ResumeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [ResumeDetailComponent],
                providers: [
                    ResumeService
                ]
            })
            .overrideTemplate(ResumeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResumeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResumeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Resume(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.resume).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
