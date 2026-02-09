import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatButton} from '@angular/material/button';
import {VoteCard} from './vote-card/vote-card';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatCardModule, MatSidenavModule, MatButton, VoteCard],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('paka-ui');
}
