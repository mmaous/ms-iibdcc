import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { AdminTemplateComponent } from './admin-template/admin-template';
import { authGuard } from './guards/auth.guard';
import { Component } from '@angular/core';
import { CustomersComponent } from './customers/customers';
import { NewCustomerComponent } from './new-customer/new-customer';
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts";
import {AccountsComponent} from "./accounts/accounts";

//  dummy component
@Component({
  template:
    '<h2 class="text-2xl font-bold text-gray-900">Welcome to the Dashboard</h2><p class="text-gray-500 mt-2">Select a menu item above.</p>',
})
export class DummyDashboardComponent {}

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'admin',
    component: AdminTemplateComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DummyDashboardComponent },
      { path: 'customers', component: CustomersComponent },
      { path: 'new-customer', component: NewCustomerComponent },
      { path: 'customer-accounts/:id', component: CustomerAccountsComponent },
      { path: 'accounts', component: AccountsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },
];
