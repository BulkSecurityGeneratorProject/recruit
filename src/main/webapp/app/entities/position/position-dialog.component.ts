import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Position } from './position.model';
import { PositionPopupService } from './position-popup.service';
import { PositionService } from './position.service';
import { Company, CompanyService } from '../company';
import { Resume, ResumeService } from '../resume';

@Component({
    selector: 'jhi-position-dialog',
    templateUrl: './position-dialog.component.html'
})
export class PositionDialogComponent implements OnInit {

    position: Position;
    isSaving: boolean;

    companies: Company[];

    resumes: Resume[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private positionService: PositionService,
        private companyService: CompanyService,
        private resumeService: ResumeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: HttpResponse<Company[]>) => { this.companies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.resumeService.query()
            .subscribe((res: HttpResponse<Resume[]>) => { this.resumes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.position.id !== undefined) {
            this.subscribeToSaveResponse(
                this.positionService.update(this.position));
        } else {
            this.subscribeToSaveResponse(
                this.positionService.create(this.position));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Position>>) {
        result.subscribe((res: HttpResponse<Position>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Position) {
        this.eventManager.broadcast({ name: 'positionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackResumeById(index: number, item: Resume) {
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
    selector: 'jhi-position-popup',
    template: ''
})
export class PositionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private positionPopupService: PositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.positionPopupService
                    .open(PositionDialogComponent as Component, params['id']);
            } else {
                this.positionPopupService
                    .open(PositionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
