import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecruitSharedModule } from '../../shared';
import {
    DeliveryService,
    DeliveryPopupService,
    DeliveryComponent,
    DeliveryDetailComponent,
    DeliveryDialogComponent,
    DeliveryPopupComponent,
    DeliveryDeletePopupComponent,
    DeliveryDeleteDialogComponent,
    deliveryRoute,
    deliveryPopupRoute,
    DeliveryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...deliveryRoute,
    ...deliveryPopupRoute,
];

@NgModule({
    imports: [
        RecruitSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeliveryComponent,
        DeliveryDetailComponent,
        DeliveryDialogComponent,
        DeliveryDeleteDialogComponent,
        DeliveryPopupComponent,
        DeliveryDeletePopupComponent,
    ],
    entryComponents: [
        DeliveryComponent,
        DeliveryDialogComponent,
        DeliveryPopupComponent,
        DeliveryDeleteDialogComponent,
        DeliveryDeletePopupComponent,
    ],
    providers: [
        DeliveryService,
        DeliveryPopupService,
        DeliveryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitDeliveryModule {}
