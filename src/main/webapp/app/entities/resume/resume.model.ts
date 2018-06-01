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
        public targetPlace?: string,
        public workTime?: any,
        public experience?: string,
        public undergo?: string,
        public education?: string,
        public targetSalary?: string,
        public targetPosition?: string,
        public userId?: number,
        public userName?: string,
        public enclosure?: string,
    ) {
    }
}
