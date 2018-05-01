import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Resume } from './resume.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Resume>;

@Injectable()
export class ResumeService {

    private resourceUrl =  SERVER_API_URL + 'api/resumes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/resumes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(resume: Resume): Observable<EntityResponseType> {
        const copy = this.convert(resume);
        return this.http.post<Resume>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(resume: Resume): Observable<EntityResponseType> {
        const copy = this.convert(resume);
        return this.http.put<Resume>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Resume>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Resume[]>> {
        const options = createRequestOption(req);
        return this.http.get<Resume[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Resume[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Resume[]>> {
        const options = createRequestOption(req);
        return this.http.get<Resume[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Resume[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Resume = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Resume[]>): HttpResponse<Resume[]> {
        const jsonResponse: Resume[] = res.body;
        const body: Resume[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Resume.
     */
    private convertItemFromServer(resume: Resume): Resume {
        const copy: Resume = Object.assign({}, resume);
        copy.birth = this.dateUtils
            .convertDateTimeFromServer(resume.birth);
        copy.workTime = this.dateUtils
            .convertDateTimeFromServer(resume.workTime);
        return copy;
    }

    /**
     * Convert a Resume to a JSON which can be sent to the server.
     */
    private convert(resume: Resume): Resume {
        const copy: Resume = Object.assign({}, resume);

        copy.birth = this.dateUtils.toDate(resume.birth);

        copy.workTime = this.dateUtils.toDate(resume.workTime);
        return copy;
    }
}
