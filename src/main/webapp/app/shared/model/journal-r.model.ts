import { IJournalS } from 'app/shared/model//journal-s.model';

export interface IJournalR {
    id?: number;
    objectId?: string;
    nameOfSociety?: string;
    onlineServices?: string;
    required?: IJournalS;
}

export class JournalR implements IJournalR {
    constructor(
        public id?: number,
        public objectId?: string,
        public nameOfSociety?: string,
        public onlineServices?: string,
        public required?: IJournalS
    ) {}
}
