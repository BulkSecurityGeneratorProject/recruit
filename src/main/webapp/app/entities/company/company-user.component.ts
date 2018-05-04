import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Company} from './company.model';
import {CompanyService} from './company.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Principal} from '../../shared';

@Component({
    selector: 'jhi-company-user',
    templateUrl: './company-user.component.html',
    styles: []
})
export class CompanyUserComponent implements OnInit {
    company: Company = new Company();
    isSaving: boolean;

    constructor(
        private companyService: CompanyService,
        private principal: Principal,
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((value) => {
            this.company.userId = value.id;
            this.companyService.findByUserId(value.id).subscribe((v) => {
                this.company = v.body;
            });
        });
        this.isSaving = false;
    }

    save() {
        this.isSaving = true;
        if (this.company.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyService.update(this.company));
        } else {
            this.subscribeToSaveResponse(
                this.companyService.create(this.company));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Company>>) {
        result.subscribe((res: HttpResponse<Company>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Company) {
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

}
