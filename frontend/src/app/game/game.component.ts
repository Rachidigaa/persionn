import { Component } from '@angular/core';
import { GameService } from '../game.service';
import { Game } from '../game.model';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent {
  game: Game | null = null;
  playerName: string = '';
  rounds: number = 0;
  gameId: number | null = null;
  move: string = '';
  playerId: number | null = null;
  strategie: string = '';

  constructor(private gameService: GameService) { }

  createGame() {
    this.gameService.createGame(this.playerName, this.rounds).subscribe(
      (game) => {
        this.game = game;
        console.log('Game created:', game);
      },
      (error) => {
        console.error('Error creating game:', error);
      }
    );
  }

  joinGame() {
    if (this.gameId && this.playerName) {
      this.gameService.joinGame(this.gameId, this.playerName).subscribe(
        (game) => {
          this.game = game;
          console.log('Joined game:', game);
        },
        (error) => {
          console.error('Error joining game:', error);
        }
      );
    }
  }

  playRound() {
    if (this.gameId && this.playerId && this.move) {
      this.gameService.playRound(this.gameId, this.playerId, this.move).subscribe(
        (result) => {
          console.log('Round played:', result);
          // Vous pouvez ajouter une logique ici pour mettre à jour l'état du jeu si nécessaire
        },
        (error) => {
          console.error('Error playing round:', error);
        }
      );
    }
  }

  quittePartie() {
    if (this.playerId && this.strategie) {
      this.gameService.quittePartie(this.playerId, this.strategie).subscribe(
        (result) => {
          console.log('Player left the game:', result);
          this.game = null; // Réinitialisez le jeu après avoir quitté
        },
        (error) => {
          console.error('Error quitting game:', error);
        }
      );
    }
  }
}
