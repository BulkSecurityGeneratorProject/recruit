import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {Position} from './position.model';
import {PositionService} from './position.service';
import {Account, Principal} from '../../shared';
import {Delivery} from '../delivery';
import {DeliveryService} from '../delivery';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'jhi-position-detail',
    templateUrl: './position-detail.component.html'
})
export class PositionDetailComponent implements OnInit, OnDestroy {
    account: Account;
    position: Position;
    delivery: Delivery = new Delivery();
    isSaving = false;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private deliveryService: DeliveryService,
        private positionService: PositionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.subscription = this.route.params.subscribe((params) => {
                this.load(params['id']);
                this.deliveryService.loadByPositionIdAndUserId({
                    posId: params['id'],
                    userId: account.id,
                }).subscribe((value) => this.delivery = value.body);
            });
        });
        this.registerChangeInPositions();
    }

    load(id) {
        this.positionService.find(id)
            .subscribe((positionResponse: HttpResponse<Position>) => {
                this.position = positionResponse.body;
            });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPositions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'positionListModification',
            (response) => this.load(this.position.id)
        );
    }

    deliveryPosition() {
        this.delivery.positionID = this.position.id;
        this.delivery.userID = this.account.id;
        this.save();
    }

    private save() {
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
        this.eventManager.broadcast({name: 'deliveryListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
