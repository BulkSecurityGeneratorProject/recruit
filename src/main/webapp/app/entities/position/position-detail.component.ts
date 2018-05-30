import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Position } from './position.model';
import { PositionService } from './position.service';
import {Account, Principal} from '../../shared';

@Component({
    selector: 'jhi-position-detail',
    templateUrl: './position-detail.component.html'
})
export class PositionDetailComponent implements OnInit, OnDestroy {
    account: Account;
    position: Position;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private positionService: PositionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
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
}
