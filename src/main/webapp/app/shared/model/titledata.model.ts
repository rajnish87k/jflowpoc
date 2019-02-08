import { Moment } from 'moment';

export interface ITitledata {
    id?: number;
    objectId?: string;
    objectName?: string;
    productId?: string;
    productTitle?: string;
    shortTitle?: string;
    creationDate?: Moment;
}

export class Titledata implements ITitledata {
    constructor(
        public id?: number,
        public objectId?: string,
        public objectName?: string,
        public productId?: string,
        public productTitle?: string,
        public shortTitle?: string,
        public creationDate?: Moment
    ) {}
}
