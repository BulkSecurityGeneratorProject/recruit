import { BaseEntity } from './../../shared';

export const enum PositionType {
    'COMPUTER',
    'INTERNET',
    'MEDICAL',
    'REALTY',
    'AD',
    'OTHER'
}

export class Position implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public describe?: string,
        public place?: string,
        public experience?: string,
        public education?: string,
        public salary?: string,
        public type?: PositionType,
        public order?: number,
        public companyId?: number,
        public resumes?: BaseEntity[],
    ) {
    }
}
