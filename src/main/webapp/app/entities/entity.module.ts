import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RecruitPositionModule } from './position/position.module';
import { RecruitCompanyModule } from './company/company.module';
import { RecruitResumeModule } from './resume/resume.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RecruitPositionModule,
        RecruitCompanyModule,
        RecruitResumeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecruitEntityModule {}
