import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccount } from '../models/account.model';
import {environment} from "../environments/environment";
import {AccountOperation} from "../models/operation.model";

@Injectable({
    providedIn: 'root'
})
export class AccountsService {
    private apiUrl = `${environment.apiUrl}`;

    constructor(private http: HttpClient) { }

    public getAccount(accountId: string): Observable<BankAccount> {
        return this.http.get<BankAccount>(this.apiUrl + '/accounts/' + accountId);
    }

    public getAccountOperations(accountId: string): Observable<Array<AccountOperation>> {
        return this.http.get<Array<AccountOperation>>(this.apiUrl + '/accounts/' + accountId + '/operations');
    }

    public debit(accountId: string, amount: number, description: string) {
        return this.http.post(this.apiUrl + '/accounts/debit', { accountId, amount, description });
    }

    public credit(accountId: string, amount: number, description: string) {
        return this.http.post(this.apiUrl + '/accounts/credit', { accountId, amount, description });
    }

    public transfer(accountSource: string, accountDestination: string, amount: number, description: string) {
        
        return this.http.post(this.apiUrl + '/accounts/transfer', { accountSource, accountDestination, amount });
    }
}