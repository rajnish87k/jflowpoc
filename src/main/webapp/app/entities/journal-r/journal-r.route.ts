import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { JournalR } from 'app/shared/model/journal-r.model';
import { JournalRService } from './journal-r.service';
import { JournalRComponent } from './journal-r.component';
import { JournalRDetailComponent } from './journal-r-detail.component';
import { JournalRUpdateComponent } from './journal-r-update.component';
import { JournalRDeletePopupComponent } from './journal-r-delete-dialog.component';
import { IJournalR } from 'app/shared/model/journal-r.model';

@Injectable({ providedIn: 'root' })
export class JournalRResolve implements Resolve<IJournalR> {
    constructor(private service: JournalRService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((journalR: HttpResponse<JournalR>) => journalR.body);
        }
        return Observable.of(new JournalR());
    }
}

export const journalRRoute: Routes = [
    {
        path: 'journal-r',
        component: JournalRComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-r/:id/view',
        component: JournalRDetailComponent,
        resolve: {
            journalR: JournalRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-r/new',
        component: JournalRUpdateComponent,
        resolve: {
            journalR: JournalRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-r/:id/edit',
        component: JournalRUpdateComponent,
        resolve: {
            journalR: JournalRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalR.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journalRPopupRoute: Routes = [
    {
        path: 'journal-r/:id/delete',
        component: JournalRDeletePopupComponent,
        resolve: {
            journalR: JournalRResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalR.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
