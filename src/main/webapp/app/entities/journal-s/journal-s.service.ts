import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJournalS } from 'app/shared/model/journal-s.model';

type EntityResponseType = HttpResponse<IJournalS>;
type EntityArrayResponseType = HttpResponse<IJournalS[]>;

@Injectable({ providedIn: 'root' })
export class JournalSService {
    private resourceUrl = SERVER_API_URL + 'api/journal-s';

    constructor(private http: HttpClient) {}

    create(journalS: IJournalS): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(journalS);
        return this.http
            .post<IJournalS>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(journalS: IJournalS): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(journalS);
        return this.http
            .put<IJournalS>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IJournalS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJournalS[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(journalS: IJournalS): IJournalS {
        const copy: IJournalS = Object.assign({}, journalS, {
            creationDate:
                journalS.creationDate != null && journalS.creationDate.isValid() ? journalS.creationDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((journalS: IJournalS) => {
            journalS.creationDate = journalS.creationDate != null ? moment(journalS.creationDate) : null;
        });
        return res;
    }
}
