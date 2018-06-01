import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DeliveryComponent } from './delivery.component';
import { DeliveryDetailComponent } from './delivery-detail.component';
import { DeliveryPopupComponent } from './delivery-dialog.component';
import { DeliveryDeletePopupComponent } from './delivery-delete-dialog.component';
import {DeliveryCompanyComponent} from './delivery-company.component';
import {DeliveryUserComponent} from './delivery-user.component';

@Injectable()
export class DeliveryResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const deliveryRoute: Routes = [
    {
        path: 'delivery',
        component: DeliveryComponent,
        resolve: {
            'pagingParams': DeliveryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'delivery-company',
        component: DeliveryCompanyComponent,
        resolve: {
            'pagingParams': DeliveryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_COMPANY'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'delivery-user',
        component: DeliveryUserComponent,
        resolve: {
            'pagingParams': DeliveryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'delivery/:id',
        component: DeliveryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveryPopupRoute: Routes = [
    {
        path: 'delivery-new',
        component: DeliveryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery/:id/edit',
        component: DeliveryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery/:id/delete',
        component: DeliveryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
