import {BaseEntity} from './../../shared';

export const enum DeliveryType {
    'DELIVERED',
    'THROUGH',
    'FAIL'
}

export class Delivery implements BaseEntity {
    constructor(
        public id?: number,
        public userID?: number,
        public positionID?: number,
        public status?: DeliveryType,
        public timestamp?: any,
        public userName?: string,
        public positionName?: string,
        public resumeID?: any,
    ) {
        this.status = DeliveryType.DELIVERED;
    }
}
