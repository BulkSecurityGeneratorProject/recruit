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
    ) {
        this.status = DeliveryType.DELIVERED;
    }
}
