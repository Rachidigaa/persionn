// src/app/app.module.ts

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; // Importez HttpClientModule ici

import { AppComponent } from './app.component'; // Importez le composant autonome
import { GameComponent } from './game/game.component';
import {FormsModule} from '@angular/forms'; // Si vous avez un composant de jeu

@NgModule({
  declarations: [
    // Retirez AppComponent de la déclaration
    GameComponent // Déclarez vos autres composants ici
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent] // Vous pouvez garder cela pour le bootstrap
})
export class AppModule { }
