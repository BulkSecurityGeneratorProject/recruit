import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {RecruitSharedModule} from '../../shared';
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
import {UEditorModule} from 'ngx-ueditor';
import {ResumeUserComponent} from './resume-user.component';

const ENTITY_STATES = [
    ...resumeRoute,
    ...resumePopupRoute,
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
        ResumeComponent,
        ResumeDetailComponent,
        ResumeDialogComponent,
        ResumeDeleteDialogComponent,
        ResumePopupComponent,
        ResumeUserComponent,
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
export class RecruitResumeModule {
}
