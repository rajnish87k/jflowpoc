import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Titledata } from 'app/shared/model/titledata.model';
import { TitledataService } from './titledata.service';
import { TitledataComponent } from './titledata.component';
import { TitledataDetailComponent } from './titledata-detail.component';
import { TitledataUpdateComponent } from './titledata-update.component';
import { TitledataDeletePopupComponent } from './titledata-delete-dialog.component';
import { ITitledata } from 'app/shared/model/titledata.model';

@Injectable({ providedIn: 'root' })
export class TitledataResolve implements Resolve<ITitledata> {
    constructor(private service: TitledataService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((titledata: HttpResponse<Titledata>) => titledata.body);
        }
        return Observable.of(new Titledata());
    }
}

export const titledataRoute: Routes = [
    {
        path: 'titledata',
        component: TitledataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.titledata.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titledata/:id/view',
        component: TitledataDetailComponent,
        resolve: {
            titledata: TitledataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.titledata.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titledata/new',
        component: TitledataUpdateComponent,
        resolve: {
            titledata: TitledataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.titledata.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'titledata/:id/edit',
        component: TitledataUpdateComponent,
        resolve: {
            titledata: TitledataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.titledata.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const titledataPopupRoute: Routes = [
    {
        path: 'titledata/:id/delete',
        component: TitledataDeletePopupComponent,
        resolve: {
            titledata: TitledataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jflowApp.titledata.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
