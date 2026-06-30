import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            let errorMessage = 'An unknown error occurred!';

            if (error.error instanceof ErrorEvent) {
                
                errorMessage = `Error: ${error.error.message}`;
            } else {
                
                if (error.status === 401 || error.status === 403) {
                    errorMessage = 'Session expired or unauthorized access. Please log in again.';
                    authService.logout(); 
                } else if (error.error && error.error.message) {
                    
                    errorMessage = error.error.message;
                } else {
                    errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
                }
            }

            alert(errorMessage);

            return throwError(() => new Error(errorMessage));
        })
    );
};