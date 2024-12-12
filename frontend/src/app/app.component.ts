// src/app/app.component.ts

import { Component } from '@angular/core';
import { GameComponent } from './game/game.component'; // Importez votre composant de jeu

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [GameComponent] // Importez le composant de jeu ici
})
export class AppComponent {
  title = 'my-angular-app';
}
