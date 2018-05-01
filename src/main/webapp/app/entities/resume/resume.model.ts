import { BaseEntity } from './../../shared';

export class Resume implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public sex?: string,
        public birth?: any,
        public email?: string,
        public wechat?: string,
        public state?: string,
        public advantage?: string,
        public place?: string,
        public workTime?: any,
        public experience?: string,
        public undergo?: string,
        public education?: string,
        public salary?: string,
        public position?: string,
        public userId?: number,
        public enclosure?: string,
        public positions?: BaseEntity[],
    ) {
    }
}
