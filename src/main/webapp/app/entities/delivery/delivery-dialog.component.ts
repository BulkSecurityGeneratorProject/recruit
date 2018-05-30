import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryPopupService } from './delivery-popup.service';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-dialog',
    templateUrl: './delivery-dialog.component.html'
})
export class DeliveryDialogComponent implements OnInit {

    delivery: Delivery;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private deliveryService: DeliveryService,
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
        if (this.delivery.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deliveryService.update(this.delivery));
        } else {
            this.subscribeToSaveResponse(
                this.deliveryService.create(this.delivery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Delivery>>) {
        result.subscribe((res: HttpResponse<Delivery>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Delivery) {
        this.eventManager.broadcast({ name: 'deliveryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-delivery-popup',
    template: ''
})
export class DeliveryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deliveryPopupService
                    .open(DeliveryDialogComponent as Component, params['id']);
            } else {
                this.deliveryPopupService
                    .open(DeliveryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
