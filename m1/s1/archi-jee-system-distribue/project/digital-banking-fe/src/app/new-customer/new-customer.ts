import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../services/customer';

@Component({
  selector: 'app-new-customer',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './new-customer.html',
})
export class NewCustomerComponent implements OnInit {
  newCustomerFormGroup!: FormGroup;
  isSaving: boolean = false;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control(null, [Validators.required, Validators.minLength(4)]),
      email: this.fb.control(null, [Validators.required, Validators.email]),
    });
  }

  handleSaveCustomer() {
    if (this.newCustomerFormGroup.invalid) return;

    this.isSaving = true;
    let customer = this.newCustomerFormGroup.value;

    this.customerService.saveCustomer(customer).subscribe({
      next: (data) => {
        alert('Client successfully registered.');
        
        this.router.navigateByUrl('/admin/customers');
      },
      error: (err) => {
        console.error(err);
        this.isSaving = false;
      },
    });
  }
}
