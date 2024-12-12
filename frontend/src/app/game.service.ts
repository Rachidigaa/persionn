// src/app/game.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from './game.model';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private readonly baseUrl = 'http://localhost:8080/game'; // Assurez-vous que le port correspond à votre backend

  constructor(private http: HttpClient) { }

  createGame(playerName: string, rounds: number): Observable<Game> {
    const params = new HttpParams()
      .set('playerName', playerName)
      .set('rounds', rounds.toString());

    return this.http.post<Game>(`${this.baseUrl}/create`, null, { params });
  }

  joinGame(gameId: number, playerName: string): Observable<Game> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerName', playerName);

    return this.http.post<Game>(`${this.baseUrl}/join`, null, { params });
  }

  playRound(gameId: number, playerId: number, move: string): Observable<string> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerId', playerId.toString())
      .set('move', move);

    return this.http.post<string>(`${this.baseUrl}/play`, null, { params });
  }

  quittePartie(playerId: number, strategie: string): Observable<string> {
    const params = new HttpParams()
      .set('playerId', playerId.toString())
      .set('strategie', strategie);

    return this.http.post<string>(`${this.baseUrl}/quitte`, null, { params });
  }
}
