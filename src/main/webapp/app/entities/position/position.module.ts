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

const ENTITY_STATES = [
    ...positionRoute,
    ...positionPopupRoute,
];

@NgModule({
    imports: [
        RecruitSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PositionComponent,
        PositionDetailComponent,
        PositionDialogComponent,
        PositionDeleteDialogComponent,
        PositionPopupComponent,
        PositionDeletePopupComponent,
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
