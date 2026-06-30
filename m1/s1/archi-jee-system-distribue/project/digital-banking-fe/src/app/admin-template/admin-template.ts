import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-admin-template',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  templateUrl: './admin-template.html',
})
export class AdminTemplateComponent {
  constructor(public authService: AuthService) {}

  handleLogout() {
    this.authService.logout();
  }
}
