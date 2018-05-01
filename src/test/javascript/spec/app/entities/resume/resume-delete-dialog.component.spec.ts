/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RecruitTestModule } from '../../../test.module';
import { ResumeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/resume/resume-delete-dialog.component';
import { ResumeService } from '../../../../../../main/webapp/app/entities/resume/resume.service';

describe('Component Tests', () => {

    describe('Resume Management Delete Component', () => {
        let comp: ResumeDeleteDialogComponent;
        let fixture: ComponentFixture<ResumeDeleteDialogComponent>;
        let service: ResumeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RecruitTestModule],
                declarations: [ResumeDeleteDialogComponent],
                providers: [
                    ResumeService
                ]
            })
            .overrideTemplate(ResumeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResumeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResumeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
