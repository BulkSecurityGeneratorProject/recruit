import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecruitSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {PositionService} from '../entities/position';

@NgModule({
    imports: [
        RecruitSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
        PositionService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitHomeModule {}
