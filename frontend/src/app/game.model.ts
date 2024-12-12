

export interface Game {
  id: number;              // Identifiant unique du jeu
  playerName: string;     // Nom du joueur
  rounds: number;         // Nombre de rounds dans le jeu
  // Ajoutez d'autres propriétés selon les besoins de votre application
  players?: string[];     // Liste des joueurs, si nécessaire
  currentRound?: number;  // Indique le round actuel, si nécessaire
  status?: string;        // État du jeu (en cours, terminé, etc.)
}
