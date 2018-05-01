/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RecruitTestModule } from '../../../test.module';
import { ResumeComponent } from '../../../../../../main/webapp/app/entities/resume/resume.component';
import { ResumeService } from '../../../../../../main/webapp/app/entities/resume/resume.service';
import { Resume } from '../../../../../../main/webapp/app/entities/resume/resume.model';

describe('Component Tests', () => {

    describe('Resume Management Component', () => {
        let comp: ResumeComponent;
        let fixture: ComponentFixture<ResumeComponent>;
        let service: ResumeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [ResumeComponent],
                providers: [
                    ResumeService
                ]
            })
            .overrideTemplate(ResumeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResumeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResumeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Resume(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.resumes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
