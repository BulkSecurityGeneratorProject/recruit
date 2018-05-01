import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecruitSharedModule } from '../../shared';
import {
    ResumeService,
    ResumePopupService,
    ResumeComponent,
    ResumeDetailComponent,
    ResumeDialogComponent,
    ResumePopupComponent,
    ResumeDeletePopupComponent,
    ResumeDeleteDialogComponent,
    resumeRoute,
    resumePopupRoute,
    ResumeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...resumeRoute,
    ...resumePopupRoute,
];

@NgModule({
    imports: [
        RecruitSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ResumeComponent,
        ResumeDetailComponent,
        ResumeDialogComponent,
        ResumeDeleteDialogComponent,
        ResumePopupComponent,
        ResumeDeletePopupComponent,
    ],
    entryComponents: [
        ResumeComponent,
        ResumeDialogComponent,
        ResumePopupComponent,
        ResumeDeleteDialogComponent,
        ResumeDeletePopupComponent,
    ],
    providers: [
        ResumeService,
        ResumePopupService,
        ResumeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitResumeModule {}
