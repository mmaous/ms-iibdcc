import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable, catchError, throwError } from 'rxjs';
import { CustomerService } from '../services/customer';
import { BankAccount } from '../models/account.model';

@Component({
  selector: 'app-customer-accounts',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './customer-accounts.html'
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: number;
  accounts$!: Observable<Array<BankAccount>>;
  errorMessage!: string;

  constructor(
      private route: ActivatedRoute,
      private customerService: CustomerService,
      private router: Router
  ) {}

  ngOnInit(): void {
    
    this.customerId = this.route.snapshot.params['id'];

    
    this.accounts$ = this.customerService.getCustomerAccounts(this.customerId).pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(() => err);
        })
    );
  }

  handleViewOperations(account: BankAccount) {
    
    this.router.navigateByUrl('/admin/accounts', { state: { accountId: account.id } });
  }
}