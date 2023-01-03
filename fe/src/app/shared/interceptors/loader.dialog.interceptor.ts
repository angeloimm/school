import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { LayoutService } from 'src/app/components/layout/service/layout.service';

@Injectable()
export class LoaderDialogInterceptor implements HttpInterceptor {
    private totalRequests = 0;
    constructor(private layoutService: LayoutService) { }
    intercept(httpRequest: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.totalRequests++;
        this.layoutService.setShowLoadingDialog(true);
        return next.handle(httpRequest).pipe(
            finalize(() => {
            this.totalRequests--;
            if( this.totalRequests === 0 ){
                this.layoutService.setShowLoadingDialog(false);
            }
        }));
    }
}