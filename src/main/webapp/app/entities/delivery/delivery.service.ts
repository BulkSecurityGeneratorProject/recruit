import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Delivery>;

@Injectable()
export class DeliveryService {

    private resourceUrl =  SERVER_API_URL + 'api/deliveries';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/deliveries';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(delivery: Delivery): Observable<EntityResponseType> {
        const copy = this.convert(delivery);
        return this.http.post<Delivery>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(delivery: Delivery): Observable<EntityResponseType> {
        const copy = this.convert(delivery);
        return this.http.put<Delivery>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Delivery>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Delivery[]>> {
        const options = createRequestOption(req);
        return this.http.get<Delivery[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Delivery[]>) => this.convertArrayResponse(res));
    }
    loadByPositionIdAndUserId(req?: any): Observable<EntityResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Delivery>(`${this.resourceUrl}/position/user`, { params: options, observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Delivery[]>> {
        const options = createRequestOption(req);
        return this.http.get<Delivery[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Delivery[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Delivery = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Delivery[]>): HttpResponse<Delivery[]> {
        const jsonResponse: Delivery[] = res.body;
        const body: Delivery[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Delivery.
     */
    private convertItemFromServer(delivery: Delivery): Delivery {
        const copy: Delivery = Object.assign({}, delivery);
        copy.timestamp = this.dateUtils
            .convertDateTimeFromServer(delivery.timestamp);
        return copy;
    }

    /**
     * Convert a Delivery to a JSON which can be sent to the server.
     */
    private convert(delivery: Delivery): Delivery {
        const copy: Delivery = Object.assign({}, delivery);

        copy.timestamp = this.dateUtils.toDate(delivery.timestamp);
        return copy;
    }

}
