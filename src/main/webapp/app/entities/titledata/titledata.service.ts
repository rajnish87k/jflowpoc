import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITitledata } from 'app/shared/model/titledata.model';

type EntityResponseType = HttpResponse<ITitledata>;
type EntityArrayResponseType = HttpResponse<ITitledata[]>;

@Injectable({ providedIn: 'root' })
export class TitledataService {
    private resourceUrl = SERVER_API_URL + 'api/titledata';

    constructor(private http: HttpClient) {}

    create(titledata: ITitledata): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(titledata);
        return this.http
            .post<ITitledata>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(titledata: ITitledata): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(titledata);
        return this.http
            .put<ITitledata>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITitledata>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITitledata[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(titledata: ITitledata): ITitledata {
        const copy: ITitledata = Object.assign({}, titledata, {
            creationDate:
                titledata.creationDate != null && titledata.creationDate.isValid() ? titledata.creationDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((titledata: ITitledata) => {
            titledata.creationDate = titledata.creationDate != null ? moment(titledata.creationDate) : null;
        });
        return res;
    }
}
