import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PositionComponent } from './position.component';
import { PositionDetailComponent } from './position-detail.component';
import { PositionPopupComponent } from './position-dialog.component';
import { PositionDeletePopupComponent } from './position-delete-dialog.component';

@Injectable()
export class PositionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'order,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const positionRoute: Routes = [
    {
        path: 'position',
        component: PositionComponent,
        resolve: {
            'pagingParams': PositionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.position.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'position/:id',
        component: PositionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.position.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const positionPopupRoute: Routes = [
    {
        path: 'position-new',
        component: PositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.position.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'position/:id/edit',
        component: PositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.position.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'position/:id/delete',
        component: PositionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'recruitApp.position.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
