import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './customers.html',
})
export class CustomersComponent implements OnInit {
  customers$!: Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup!: FormGroup;

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    
    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
    let kw = this.searchFormGroup?.value.keyword;
    this.customers$ = this.customerService.searchCustomers(kw).pipe(
      catchError((err) => {
        this.errorMessage = err.message;
        return throwError(() => err);
      }),
    );
  }

  handleDeleteCustomer(c: Customer) {
    let conf = confirm('Are you sure you want to delete this customer?');
    if (!conf) return;

    this.customerService.deleteCustomer(c.id).subscribe({
      next: (resp) => {
        
        this.handleSearchCustomers();
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
