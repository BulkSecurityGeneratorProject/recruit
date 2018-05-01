import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Resume } from './resume.model';
import { ResumeService } from './resume.service';

@Component({
    selector: 'jhi-resume-detail',
    templateUrl: './resume-detail.component.html'
})
export class ResumeDetailComponent implements OnInit, OnDestroy {

    resume: Resume;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private resumeService: ResumeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInResumes();
    }

    load(id) {
        this.resumeService.find(id)
            .subscribe((resumeResponse: HttpResponse<Resume>) => {
                this.resume = resumeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInResumes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'resumeListModification',
            (response) => this.load(this.resume.id)
        );
    }
}
