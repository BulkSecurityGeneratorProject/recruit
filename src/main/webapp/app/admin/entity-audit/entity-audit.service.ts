import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {EntityAuditEvent} from './entity-audit-event.model';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class EntityAuditService {

    constructor(private http: HttpClient) { }

    getAllAudited(): Observable<string[]> {
        return this.http.get<string[]>('api/audits/entity/all');
    }

    findByEntity(entity, limit): Observable<EntityAuditEvent[]> {
        return this.http.get<EntityAuditEvent[]>('api/audits/entity/changes', {
            params: {
                entityType: entity,
                limit: limit.toString()
            }
        });
    }

    getPrevVersion(qualifiedName, entityId, commitVersion) {
        return this.http.get<any>('api/audits/entity/changes/version/previous', {
            params: {
                qualifiedName: qualifiedName.toString(),
                entityId: entityId.toString(),
                commitVersion: commitVersion.toString()
            }
        });
    }
}
