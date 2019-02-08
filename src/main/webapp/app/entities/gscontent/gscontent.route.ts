import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Gscontent } from 'app/shared/model/gscontent.model';
import { GscontentService } from './gscontent.service';
import { GscontentComponent } from './gscontent.component';
import { GscontentDetailComponent } from './gscontent-detail.component';
import { GscontentUpdateComponent } from './gscontent-update.component';
import { GscontentDeletePopupComponent } from './gscontent-delete-dialog.component';
import { IGscontent } from 'app/shared/model/gscontent.model';

@Injectable({ providedIn: 'root' })
export class GscontentResolve implements Resolve<IGscontent> {
    constructor(private service: GscontentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((gscontent: HttpResponse<Gscontent>) => gscontent.body);
        }
        return Observable.of(new Gscontent());
    }
}

export const gscontentRoute: Routes = [
    {
        path: 'gscontent',
        component: GscontentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.gscontent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gscontent/:id/view',
        component: GscontentDetailComponent,
        resolve: {
            gscontent: GscontentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.gscontent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gscontent/new',
        component: GscontentUpdateComponent,
        resolve: {
            gscontent: GscontentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.gscontent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gscontent/:id/edit',
        component: GscontentUpdateComponent,
        resolve: {
            gscontent: GscontentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.gscontent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gscontentPopupRoute: Routes = [
    {
        path: 'gscontent/:id/delete',
        component: GscontentDeletePopupComponent,
        resolve: {
            gscontent: GscontentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.gscontent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
