import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJournalR } from 'app/shared/model/journal-r.model';

type EntityResponseType = HttpResponse<IJournalR>;
type EntityArrayResponseType = HttpResponse<IJournalR[]>;

@Injectable({ providedIn: 'root' })
export class JournalRService {
    private resourceUrl = SERVER_API_URL + 'api/journal-rs';

    constructor(private http: HttpClient) {}

    create(journalR: IJournalR): Observable<EntityResponseType> {
        return this.http.post<IJournalR>(this.resourceUrl, journalR, { observe: 'response' });
    }

    update(journalR: IJournalR): Observable<EntityResponseType> {
        return this.http.put<IJournalR>(this.resourceUrl, journalR, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJournalR>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJournalR[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
