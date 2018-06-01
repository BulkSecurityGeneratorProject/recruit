import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {Account, ITEMS_PER_PAGE, LoginModalService, Principal} from '../shared';
import {Position, PositionService} from '../entities/position';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    positions: Position[] = [];
    type = 'COMPUTER';
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any = 1;
    predicate: any = 'order';
    reverse: any;
    previousPage: any;
    currentSearch: string;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private positionService: PositionService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadPositionByType();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.loadPositionByType();
            });
        });
    }

    private loadPositionByType() {
        if (this.currentSearch) {
            this.positionService.search({
                page: this.page - 1,
                query: this.currentSearch,
                size: this.itemsPerPage,
                sort: this.sort()
            }).subscribe(
                (res: HttpResponse<Position[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            return;
        }
        this.positionService.queryByType({
            page: this.page - 1,
            size: this.itemsPerPage,
            type: this.type,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Position[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    navChange(type: string) {
        this.type = type;
        this.loadPositionByType();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.page = page;
            this.loadPositionByType();
        }
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.type = 'SEARCH';
        this.loadPositionByType();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.type = 'COMPUTER';
        this.loadPositionByType();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.positions = data;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
        this.positions = [];
    }

}
