import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { JournalS } from 'app/shared/model/journal-s.model';
import { JournalSService } from './journal-s.service';
import { JournalSComponent } from './journal-s.component';
import { JournalSDetailComponent } from './journal-s-detail.component';
import { JournalSUpdateComponent } from './journal-s-update.component';
import { JournalSDeletePopupComponent } from './journal-s-delete-dialog.component';
import { IJournalS } from 'app/shared/model/journal-s.model';

@Injectable({ providedIn: 'root' })
export class JournalSResolve implements Resolve<IJournalS> {
    constructor(private service: JournalSService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((journalS: HttpResponse<JournalS>) => journalS.body);
        }
        return Observable.of(new JournalS());
    }
}

export const journalSRoute: Routes = [
    {
        path: 'journal-s',
        component: JournalSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-s/:id/view',
        component: JournalSDetailComponent,
        resolve: {
            journalS: JournalSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-s/new',
        component: JournalSUpdateComponent,
        resolve: {
            journalS: JournalSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'journal-s/:id/edit',
        component: JournalSUpdateComponent,
        resolve: {
            journalS: JournalSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journalSPopupRoute: Routes = [
    {
        path: 'journal-s/:id/delete',
        component: JournalSDeletePopupComponent,
        resolve: {
            journalS: JournalSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.journalS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
