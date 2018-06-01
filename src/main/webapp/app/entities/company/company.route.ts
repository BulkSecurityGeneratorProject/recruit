import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {CompanyComponent} from './company.component';
import {CompanyDetailComponent} from './company-detail.component';
import {CompanyPopupComponent} from './company-dialog.component';
import {CompanyDeletePopupComponent} from './company-delete-dialog.component';
import {CompanyUserComponent} from './company-user.component';

@Injectable()
export class CompanyResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

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

export const companyRoute: Routes = [
    {
        path: 'company',
        component: CompanyComponent,
        resolve: {
            'pagingParams': CompanyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company/:id',
        component: CompanyDetailComponent,
        data: {
            authorities: ['ROLE_COMPANY', 'ROLE_USER'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
    , {
        path: 'company-user',
        component: CompanyUserComponent,
        data: {
            authorities: ['ROLE_COMPANY'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyPopupRoute: Routes = [
    {
        path: 'company-new',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company/:id/edit',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company/:id/delete',
        component: CompanyDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'recruitApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
