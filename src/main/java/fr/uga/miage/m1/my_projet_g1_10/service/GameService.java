package fr.uga.miage.m1.my_projet_g1_10.service;

import fr.uga.miage.m1.my_projet_g1_10.Repository.GameRepository;
import fr.uga.miage.m1.my_projet_g1_10.Repository.PlayerRepository;
import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.strategiesCreators.StrategieFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public Game createGame(String playerName, int rounds) {
        Game game = new Game();
        game.setRounds(rounds);

        Player player = new Player();
        player.setName(playerName);
        player.setGame(game);

        game.addPlayer(player);
        gameRepository.save(game);
        return game;
    }

    public Optional<Game> joinGame(Long gameId, String playerName) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            if (!game.isFull() && !game.isFinished()) {
                Player player = new Player();
                player.setName(playerName);
                player.setGame(game);
                game.getPlayers().add(player);

                if (game.isFull()) {
                    game.setStatus("STARTED");
                }

                playerRepository.save(player);
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    public Map<String, Object> playRound(Long gameId, Long playerId, String playerMove) {
        Map<String, Object> response = new HashMap<>();

        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            response.put("message", "Game not found.");
            return response;
        }

        Game game = optionalGame.get();

        if ("FINISHED".equals(game.getStatus())) {
            response.put("message", "Game is already finished.");
            response.put("scores", getGameScores(game));
            return response;
        }

        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalPlayer.isEmpty()) {
            response.put("message", "Player not found.");
            return response;
        }

        Player currentPlayer = optionalPlayer.get();

        if (currentPlayer.getCurrentMove() != null) {
            response.put("message", "Player has already submitted a move for this round.");
            return response;
        }

        Decision decision = Decision.valueOf(playerMove);
        submitPlayerMove(currentPlayer, decision);

        Player otherPlayer = findOtherPlayer(game, playerId);

        if (otherPlayer != null && !otherPlayer.getPresent()) {
            submitOtherPlayerMove(otherPlayer);
        }

        if (otherPlayer != null && otherPlayer.getCurrentMove() != null) {
            String result = processRoundEnd(game, currentPlayer, otherPlayer);
            response.put("message", result);
            response.put("scores", getGameScores(game));
            return response;
        }

        response.put("message", "Waiting for the other player to submit their move.");
        response.put("scores", getGameScores(game));
        return response;
    }

    // Helper method to get scores
    private Map<String, Integer> getGameScores(Game game) {
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : game.getPlayers()) {
            scores.put(player.getName(), player.getScore());
        }
        return scores;
    }






    public void submitPlayerMove(Player player, Decision decision) {
        player.setCurrentMove(decision);
        player.addMoves(decision);
        playerRepository.save(player);
    }

    public Player findOtherPlayer(Game game, Long playerId) {
        return game.getPlayers().stream()
                .filter(p -> !p.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public void submitOtherPlayerMove(Player otherPlayer) {
        Decision decider = StrategieFactory.getStrategie(otherPlayer.getStrategie()).decider(otherPlayer.getMoveHistory());
        otherPlayer.setCurrentMove(decider);
        playerRepository.save(otherPlayer);
    }

    public String processRoundEnd(Game game, Player currentPlayer, Player otherPlayer) {
        // Calculez les scores en fonction des mouvements
        String result = compareMovesAndCalculateScore(currentPlayer, otherPlayer);

        // Réinitialisez les mouvements des deux joueurs
        resetMoves(currentPlayer, otherPlayer);

        // Incrémentez le tour uniquement après avoir réinitialisé les mouvements
        game.incrementRound();

        // Si la partie est terminée, mettez à jour le statut
        if (game.isFinished()) {
            game.setStatus("FINISHED");
            gameRepository.save(game); // Sauvegarder les modifications
            return "Game finished! " + result;
        }

        // Sauvegardez l'état actuel
        gameRepository.save(game);
        playerRepository.save(currentPlayer);
        playerRepository.save(otherPlayer);

        return result; // Retournez le résultat du tour
    }


    public void resetMoves(Player currentPlayer, Player otherPlayer) {
        currentPlayer.setCurrentMove(null);
        otherPlayer.setCurrentMove(null);
        playerRepository.save(currentPlayer);
        playerRepository.save(otherPlayer);
    }



    public Map<String, Object> getGameStatus(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();

            // Préparez les informations sur le jeu et les joueurs
            Map<String, Object> response = new HashMap<>();
            response.put("status", game.getStatus());
            response.put("currentRound", game.getCurrentRound());
            response.put("rounds", game.getRounds());
            response.put("players", game.getPlayers().stream().map(player -> {
                Map<String, Object> playerData = new HashMap<>();
                playerData.put("id", player.getId());
                playerData.put("name", player.getName());
                playerData.put("currentMove", player.getCurrentMove());
                playerData.put("score", player.getScore());
                return playerData;
            }).toList());
            return response;
        }

        return null; // Retournez null si le jeu n'existe pas
    }




    public String compareMovesAndCalculateScore(Player player1, Player player2) {
        Decision move1 = player1.getCurrentMove();
        Decision move2 = player2.getCurrentMove();

        if (move1 == Decision.TRAHIR && move2 == Decision.TRAHIR) {
            player1.incrementScore(1);
            player2.incrementScore(1);
            return "Both players betrayed. Both get 1 point.";
        } else if (move1 == Decision.COOPERER && move2 == Decision.COOPERER) {
            player1.incrementScore(3);
            player2.incrementScore(3);
            return "Both players cooperated. Both get 3 points.";
        } else if (move1 == Decision.TRAHIR && move2 == Decision.COOPERER) {
            player1.incrementScore(5);
            player2.incrementScore(0);
            return player1.getName() + " betrayed, " + player2.getName() + " cooperated. " + player1.getName() + " gets 5 points.";
        } else if (move1 == Decision.COOPERER && move2 == Decision.TRAHIR) {
            player1.incrementScore(0);
            player2.incrementScore(5);
            return player1.getName() + " cooperated, " + player2.getName() + " betrayed. " + player2.getName() + " gets 5 points.";
        }
        return "Error comparing moves.";
    }

    public String quitterPartie(Long playerId, String strategiePlayer) {
        Strategie strategie = Strategie.valueOf(strategiePlayer);
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        System.out.println(strategiePlayer);
        System.out.println(strategie);
        if (optionalPlayer.isPresent()) {
            Player currentPlayer = optionalPlayer.get();
            currentPlayer.changePresent(); // Le joueur quitte la partie
            currentPlayer.setStrategie(strategie); // Applique la stratégie choisie
            playerRepository.save(currentPlayer);
            return "Joueur " + currentPlayer.getName() + " a quitté la partie avec la stratégie " + strategiePlayer;
        }

        return "Player not found";
    }

    public Decision generateMove(Player player) {
        // Utilisez une fabrique de stratégie pour générer un mouvement en fonction de la stratégie
        return StrategieFactory.getStrategie(player.getStrategie()).decider(player.getMoveHistory());
    }

    public void generateAndSubmitMove(Player player) {
        Decision move = generateMove(player);
        player.setCurrentMove(move);
        player.addMoves(move);
        playerRepository.save(player);
    }



    public Map<String, Object> getGameScore(Long gameId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            throw new IllegalArgumentException("Game not found.");
        }

        Game game = optionalGame.get();

        // Vérifiez si la partie est terminée
        if (!"FINISHED".equals(game.getStatus())) {
            throw new IllegalArgumentException("Game is not finished yet.");
        }

        // Préparez les données des scores des joueurs
        Map<String, Object> response = new HashMap<>();
        response.put("scores", game.getPlayers().stream().map(player -> {
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("id", player.getId());
            playerData.put("name", player.getName());
            playerData.put("score", player.getScore());
            return playerData;
        }).toList());

        return response;
    }


}
