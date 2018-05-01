import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Resume } from './resume.model';
import { ResumePopupService } from './resume-popup.service';
import { ResumeService } from './resume.service';
import { Position, PositionService } from '../position';

@Component({
    selector: 'jhi-resume-dialog',
    templateUrl: './resume-dialog.component.html'
})
export class ResumeDialogComponent implements OnInit {

    resume: Resume;
    isSaving: boolean;

    positions: Position[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private resumeService: ResumeService,
        private positionService: PositionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.positionService.query()
            .subscribe((res: HttpResponse<Position[]>) => { this.positions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.eventManager.broadcast({ name: 'resumeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPositionById(index: number, item: Position) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
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
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
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
