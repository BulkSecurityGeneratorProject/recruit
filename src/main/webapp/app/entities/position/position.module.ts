import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecruitSharedModule } from '../../shared';
import {
    PositionService,
    PositionPopupService,
    PositionComponent,
    PositionDetailComponent,
    PositionDialogComponent,
    PositionPopupComponent,
    PositionDeletePopupComponent,
    PositionDeleteDialogComponent,
    positionRoute,
    positionPopupRoute,
    PositionResolvePagingParams,
} from './';
import { UEditorModule } from 'ngx-ueditor';
import { PositionCompanyComponent } from './position-company.component';

const ENTITY_STATES = [
    ...positionRoute,
    ...positionPopupRoute,
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
        PositionComponent,
        PositionDetailComponent,
        PositionDialogComponent,
        PositionDeleteDialogComponent,
        PositionPopupComponent,
        PositionDeletePopupComponent,
        PositionCompanyComponent
    ],
    entryComponents: [
        PositionComponent,
        PositionDialogComponent,
        PositionPopupComponent,
        PositionDeleteDialogComponent,
        PositionDeletePopupComponent,
    ],
    providers: [
        PositionService,
        PositionPopupService,
        PositionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitPositionModule {}
