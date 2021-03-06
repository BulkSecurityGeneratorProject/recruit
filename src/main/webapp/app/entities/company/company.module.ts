import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecruitSharedModule } from '../../shared';
import {
    CompanyService,
    CompanyPopupService,
    CompanyComponent,
    CompanyDetailComponent,
    CompanyDialogComponent,
    CompanyPopupComponent,
    CompanyDeletePopupComponent,
    CompanyDeleteDialogComponent,
    companyRoute,
    companyPopupRoute,
    CompanyResolvePagingParams,
} from './';
import { UEditorModule } from 'ngx-ueditor';
import { CompanyUserComponent } from './company-user.component';

const ENTITY_STATES = [
    ...companyRoute,
    ...companyPopupRoute,
];

@NgModule({
    imports: [
        RecruitSharedModule,
        UEditorModule.forRoot({
            // 指定ueditor.js路径目录
            path: 'content/js/ueditor/',
            // 默认全局配置项
            options: {
                themePath: '/content/js/ueditor/themes/'
            }
        }),
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyComponent,
        CompanyDetailComponent,
        CompanyDialogComponent,
        CompanyDeleteDialogComponent,
        CompanyPopupComponent,
        CompanyUserComponent,
        CompanyDeletePopupComponent,
    ],
    entryComponents: [
        CompanyComponent,
        CompanyDialogComponent,
        CompanyPopupComponent,
        CompanyDeleteDialogComponent,
        CompanyDeletePopupComponent,
    ],
    providers: [
        CompanyService,
        CompanyPopupService,
        CompanyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitCompanyModule {}
