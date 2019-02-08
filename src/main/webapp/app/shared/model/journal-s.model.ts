import { Moment } from 'moment';
import { IJournalR } from 'app/shared/model//journal-r.model';

export const enum Language {
    ENGLISH = 'ENGLISH',
    GERMAN = 'GERMAN',
    DUTCH = 'DUTCH'
}

export const enum CompanyGroup {
    AA = 'AA',
    BB = 'BB',
    CC = 'CC'
}

export interface IJournalS {
    id?: number;
    objectId?: string;
    objectName?: string;
    productId?: string;
    title?: string;
    shortTitle?: string;
    primaryLanguage?: Language;
    companyGroup?: CompanyGroup;
    publisher?: string;
    publishingSegment?: string;
    imprint?: string;
    medium?: string;
    creationDate?: Moment;
    journalRS?: IJournalR[];
}

export class JournalS implements IJournalS {
    constructor(
        public id?: number,
        public objectId?: string,
        public objectName?: string,
        public productId?: string,
        public title?: string,
        public shortTitle?: string,
        public primaryLanguage?: Language,
        public companyGroup?: CompanyGroup,
        public publisher?: string,
        public publishingSegment?: string,
        public imprint?: string,
        public medium?: string,
        public creationDate?: Moment,
        public journalRS?: IJournalR[]
    ) {}
}
