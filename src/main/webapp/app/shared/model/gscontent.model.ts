import { Moment } from 'moment';

export interface IGscontent {
    id?: number;
    objectId?: string;
    repoContentPath?: string;
    creationDate?: Moment;
    parentId?: string;
}

export class Gscontent implements IGscontent {
    constructor(
        public id?: number,
        public objectId?: string,
        public repoContentPath?: string,
        public creationDate?: Moment,
        public parentId?: string
    ) {}
}
