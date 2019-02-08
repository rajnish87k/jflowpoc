import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGscontent } from 'app/shared/model/gscontent.model';

type EntityResponseType = HttpResponse<IGscontent>;
type EntityArrayResponseType = HttpResponse<IGscontent[]>;

@Injectable({ providedIn: 'root' })
export class GscontentService {
    private resourceUrl = SERVER_API_URL + 'api/gscontents';

    constructor(private http: HttpClient) {}

    create(gscontent: IGscontent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gscontent);
        return this.http
            .post<IGscontent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(gscontent: IGscontent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gscontent);
        return this.http
            .put<IGscontent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGscontent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGscontent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(gscontent: IGscontent): IGscontent {
        const copy: IGscontent = Object.assign({}, gscontent, {
            creationDate:
                gscontent.creationDate != null && gscontent.creationDate.isValid() ? gscontent.creationDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((gscontent: IGscontent) => {
            gscontent.creationDate = gscontent.creationDate != null ? moment(gscontent.creationDate) : null;
        });
        return res;
    }
}
