import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  selectedFlag: string = 'assets/images/flags/es.avif';
  showLanguages: boolean = false;

  constructor(private translateService: TranslateService) { }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    window.location.reload(); // Recargar para actualizar la vista.
  }

  changeLanguage(lang: string, flagUrl: string): void {
    this.translateService.use(lang);
    this.selectedFlag = flagUrl;
    this.showLanguages = false;
  }
}