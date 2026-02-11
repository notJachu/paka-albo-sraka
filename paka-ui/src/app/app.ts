import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatButton, MatIconButton} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {MatIcon} from '@angular/material/icon';
import {VoteForm} from './vote-form/vote-form';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatCardModule, MatSidenavModule, MatButton, FormsModule, MatIcon, MatIconButton, VoteForm],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('paka-ui');
  searchQuery: string = '';

  onSearch() {
    console.log('Wyszukiwanie frazy:', this.searchQuery);
    // Tutaj dodaj logikę wysyłania zapytania do serwisu
  }

  openFilters() {
    console.log('Otwieranie zaawansowanych filtrów');
    // Tutaj np. otwarcie MatDialog z filtrami
  }
}
