import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Resume } from './resume.model';
import { ResumeService } from './resume.service';

@Injectable()
export class ResumePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private resumeService: ResumeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.resumeService.find(id)
                    .subscribe((resumeResponse: HttpResponse<Resume>) => {
                        const resume: Resume = resumeResponse.body;
                        resume.birth = this.datePipe
                            .transform(resume.birth, 'yyyy-MM-ddTHH:mm:ss');
                        resume.workTime = this.datePipe
                            .transform(resume.workTime, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.resumeModalRef(component, resume);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.resumeModalRef(component, new Resume());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    resumeModalRef(component: Component, resume: Resume): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.resume = resume;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
