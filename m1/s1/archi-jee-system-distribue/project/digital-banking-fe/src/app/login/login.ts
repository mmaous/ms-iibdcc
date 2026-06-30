import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string | undefined;
  isLoading: boolean = false; 

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
  }

  handleLogin() {
    this.isLoading = true; 
    this.errorMessage = undefined; 

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (data) => {
        this.authService.loadProfile(data);
        this.router.navigate(['/admin/dashboard']);
        this.isLoading = false; 
      },
      error: (err) => {
        this.errorMessage = 'Access Denied: Invalid Credentials.';
        this.isLoading = false; 
        console.error(err);
      },
    });
  }
}
