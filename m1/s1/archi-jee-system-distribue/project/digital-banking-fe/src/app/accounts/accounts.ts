import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountsService } from '../services/accounts';
import { Observable, catchError, throwError } from 'rxjs';
import { BankAccount } from '../models/account.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {AccountOperation} from "../models/operation.model";

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './accounts.html'
})
export class AccountsComponent implements OnInit {
  accountFormGroup!: FormGroup;
  operationFormGroup!: FormGroup;

  accountObservable$!: Observable<BankAccount>;
  operationsObservable$!: Observable<Array<AccountOperation>>;

  errorMessage!: string;
  isProcessing: boolean = false;

  constructor(private fb: FormBuilder, private accountsService: AccountsService, private router: Router) {}

  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control('', Validators.required)
    });

    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control('DEBIT'),
      amount: this.fb.control(0, [Validators.required, Validators.min(0.1)]),
      description: this.fb.control('', Validators.required),
      accountDestination: this.fb.control('')
    });

    // Auto-load if navigated from the Customer Accounts view
    const state = history.state;
    if (state && state.accountId) {
      this.accountFormGroup.controls['accountId'].setValue(state.accountId);
      this.handleSearchAccount();
    }
  }

  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId;

    this.accountObservable$ = this.accountsService.getAccount(accountId).pipe(
        catchError(err => {
          this.errorMessage = "Account not found.";
          return throwError(() => err);
        })
    );

    this.operationsObservable$ = this.accountsService.getAccountOperations(accountId).pipe(
        catchError(err => throwError(() => err))
    );
  }

  handleAccountOperation() {
    if (this.operationFormGroup.invalid) return;
    this.isProcessing = true;

    let accountId = this.accountFormGroup.value.accountId;
    let operationType = this.operationFormGroup.value.operationType;
    let amount = this.operationFormGroup.value.amount;
    let description = this.operationFormGroup.value.description;
    let accountDestination = this.operationFormGroup.value.accountDestination;

    if (operationType === 'DEBIT') {
      this.accountsService.debit(accountId, amount, description).subscribe({
        next: (data) => this.refreshAndReset(),
        error: (err) => this.handleError(err)
      });
    } else if (operationType === 'CREDIT') {
      this.accountsService.credit(accountId, amount, description).subscribe({
        next: (data) => this.refreshAndReset(),
        error: (err) => this.handleError(err)
      });
    } else if (operationType === 'TRANSFER') {
      this.accountsService.transfer(accountId, accountDestination, amount, description).subscribe({
        next: (data) => this.refreshAndReset(),
        error: (err) => this.handleError(err)
      });
    }
  }

  private refreshAndReset() {
    this.isProcessing = false;
    alert("Operation Successful");
    this.operationFormGroup.reset({ operationType: 'DEBIT', amount: 0, description: '', accountDestination: '' });
    this.handleSearchAccount(); // Refresh history and balance
  }

  private handleError(err: any) {
    this.isProcessing = false;
    alert("Operation Failed: " + (err.error?.message || err.message));
  }
}