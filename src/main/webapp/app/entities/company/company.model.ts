import { BaseEntity } from './../../shared';

export const enum CompanyType {
    'COMPUTER',
    'INTERNET',
    'MEDICAL',
    'REALTY',
    'AD',
    'OTHER'
}

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public financing?: boolean,
        public personNumber?: string,
        public describe?: string,
        public address?: string,
        public userId?: number,
        public type?: CompanyType,
        public order?: number,
        public positions?: BaseEntity[],
    ) {
        this.financing = false;
    }
}
