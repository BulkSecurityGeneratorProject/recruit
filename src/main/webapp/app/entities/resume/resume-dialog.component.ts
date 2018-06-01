import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse, HttpErrorResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {Resume} from './resume.model';
import {ResumePopupService} from './resume-popup.service';
import {ResumeService} from './resume.service';

@Component({
    selector: 'jhi-resume-dialog',
    templateUrl: './resume-dialog.component.html'
})
export class ResumeDialogComponent implements OnInit {

    resume: Resume;
    isSaving: boolean;
    uploading = false;

    constructor(
        public activeModal: NgbActiveModal,
        private resumeService: ResumeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.resume.id !== undefined) {
            this.subscribeToSaveResponse(
                this.resumeService.update(this.resume));
        } else {
            this.subscribeToSaveResponse(
                this.resumeService.create(this.resume));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Resume>>) {
        result.subscribe((res: HttpResponse<Resume>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Resume) {
        this.eventManager.broadcast({name: 'resumeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    upload($event) {
        if ($event && $event.target.files && $event.target.files[0]) {
            const file = $event.target.files[0];
            this.uploading = true;
            this.resumeService.upload(file).subscribe((res) => {
                this.resume.enclosure = res.headers.get('X-recruitApp-params');
                this.uploading = false;
            }, ((error) => {
                console.log(error);
                this.uploading = false;
            }));
        }
    }
}

@Component({
    selector: 'jhi-resume-popup',
    template: ''
})
export class ResumePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resumePopupService: ResumePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.resumePopupService
                    .open(ResumeDialogComponent as Component, params['id']);
            } else {
                this.resumePopupService
                    .open(ResumeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
