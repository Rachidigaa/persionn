/*package fr.uga.miage.m1.my_projet_g1_10.service;

import fr.uga.miage.m1.my_projet_g1_10.Repository.GameRepository;
import fr.uga.miage.m1.my_projet_g1_10.Repository.PlayerRepository;
import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.strategies.Graduel;
import fr.uga.miage.m1.my_projet_g1_10.strategies.IStrategie;
import fr.uga.miage.m1.my_projet_g1_10.strategiesCreators.StrategieFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_createsGameWithPlayer() {
        Game game = new Game();
        Player player = new Player();

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame("Player1", 5);

        assertNotNull(createdGame);
        assertEquals(1, createdGame.getPlayers().size());
        assertEquals("Player1", createdGame.getPlayers().get(0).getName());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    /*@Test
    void joinGame_addsPlayerToGame() {
        Game game = new Game();
        game.setPlayers(new ArrayList<>());
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.joinGame(1L, "NewPlayer");

        assertFalse(result.isPresent());
        assertEquals(1, game.getPlayers().size());
        assertEquals("NewPlayer", game.getPlayers().get(0).getName());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void joinGame_failsWhenGameIsFullOrFinished() {
        Game game = new Game();
        game.setStatus("FINISHED");
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.joinGame(1L, "NewPlayer");

        assertFalse(result.isPresent());
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void joinGame_failsWhenGameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Game> result = gameService.joinGame(1L, "NewPlayer");

        assertFalse(result.isPresent());
    }

    @Test
    void playRound_failsWhenGameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        String result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Game or Player not found.", result);
    }

    @Test
    void playRound_failsWhenGameIsFinished() {
        Game game = new Game();
        game.setStatus("FINISHED");
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        String result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Game is already finished.", result);
    }

    @Test
    void playRound_failsWhenPlayerNotFound() {
        Game game = new Game();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        String result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Game or Player not found.", result);
    }

    @Test
    void playRound_failsWhenPlayerAlreadyPlayed() {
        Game game = new Game();
        Player player = new Player();
        player.setCurrentMove(Decision.COOPERER);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        String result = gameService.playRound(1L, 1L, "COOPERER");

        assertEquals("Player has already submitted a move for this round.", result);
    }

    @Test
    void compareMovesAndCalculateScore_bothPlayersBetray() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setCurrentMove(Decision.TRAHIR);
        player2.setCurrentMove(Decision.TRAHIR);

        String result = gameService.compareMovesAndCalculateScore(player1, player2);

        assertEquals("Both players betrayed. Both get 1 point.", result);
        assertEquals(1, player1.getScore());
        assertEquals(1, player2.getScore());
    }

    @Test
    void compareMovesAndCalculateScore_bothPlayersCooperate() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setCurrentMove(Decision.COOPERER);
        player2.setCurrentMove(Decision.COOPERER);

        String result = gameService.compareMovesAndCalculateScore(player1, player2);

        assertEquals("Both players cooperated. Both get 3 points.", result);
        assertEquals(3, player1.getScore());
        assertEquals(3, player2.getScore());
    }

    @Test
    void compareMovesAndCalculateScore_player1BetraysPlayer2Cooperates() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setCurrentMove(Decision.TRAHIR);
        player2.setCurrentMove(Decision.COOPERER);

        String result = gameService.compareMovesAndCalculateScore(player1, player2);

        assertEquals(player1.getName()+" betrayed, "+player2.getName()+" cooperated. "+player1.getName()+" gets 5 points.", result);
        assertEquals(5, player1.getScore());
        assertEquals(0, player2.getScore());
    }

    @Test
    void quitterPartie_changesPlayerStrategy() {
        Player player = new Player();
        player.setName("Player1");
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        String result = gameService.quitterPartie(1L, "TOUJOURSTRAHIR");

        assertEquals("Joueur Player1 a quitté La partie", result);
        assertEquals(Strategie.TOUJOURSTRAHIR, player.getStrategie());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void quitterPartie_failsWhenPlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        String result = gameService.quitterPartie(1L, "TOUJOURSTRAHIR");

        assertEquals("Player not found", result);
    }

    @Test
    void submitOtherPlayerMove_setsStrategieMoveAndSavesPlayer() {
        // Mock static factory method `StrategieFactory.getStrategie`
        try (MockedStatic<StrategieFactory> mockedFactory = mockStatic(StrategieFactory.class)) {
            // Configuration de `Player` avec une stratégie
            Player otherPlayer = new Player();
            otherPlayer.setStrategie(Strategie.TOUJOURSTRAHIR);
            otherPlayer.setMoveHistory(Collections.emptyList());

            // Créer une stratégie mockée qui renvoie toujours `TRAHIR`
            IStrategie mockStrategie = opponent -> Decision.TRAHIR;
            mockedFactory.when(() -> StrategieFactory.getStrategie(Strategie.TOUJOURSTRAHIR))
                    .thenReturn(mockStrategie);

            // Exécution de la méthode `submitOtherPlayerMove`
            gameService.submitOtherPlayerMove(otherPlayer);

            // Vérification que le mouvement est bien défini sur `TRAHIR`
            assertEquals(Decision.TRAHIR, otherPlayer.getCurrentMove());
            // Vérification que le joueur est sauvegardé
            verify(playerRepository, times(1)).save(otherPlayer);
        }
    }

    @Test
    void joinGame_addsPlayerToGameAndStartsGameWhenFull() {
        Game game = new Game();
        game.setPlayers(new ArrayList<>());
        game.setRounds(2); // Suppose the game can only have 2 players to be full
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        game.getPlayers().add(new Player()); // Adding one player to simulate the game being almost full

        Optional<Game> result = gameService.joinGame(1L, "NewPlayer");

        assertTrue(result.isPresent());
        assertEquals(2, game.getPlayers().size()); // Game should now be full
        assertEquals("NewPlayer", game.getPlayers().get(1).getName());
        verify(playerRepository, times(1)).save(any(Player.class));
        assertEquals("STARTED", game.getStatus()); // Game status should be "STARTED"
    }

    @Test
    void submitPlayerMove_setsDecisionAndSavesPlayer() {
        Player player = new Player();

        gameService.submitPlayerMove(player, Decision.COOPERER);

        assertEquals(Decision.COOPERER, player.getCurrentMove());
        assertTrue(player.getMoveHistory().contains(Decision.COOPERER));
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void findOtherPlayer_returnsOtherPlayerInGame() {
        Game game = new Game();
        Player player1 = new Player();
        player1.setId(1L);
        Player player2 = new Player();
        player2.setId(2L);

        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        Player otherPlayer = gameService.findOtherPlayer(game, 1L);

        assertNotNull(otherPlayer);
        assertEquals(2L, otherPlayer.getId());
    }

    @Test
    void resetMoves_resetsCurrentMoves() {
        Player player1 = new Player();
        player1.setCurrentMove(Decision.COOPERER);
        Player player2 = new Player();
        player2.setCurrentMove(Decision.TRAHIR);

        gameService.resetMoves(player1, player2);

        assertNull(player1.getCurrentMove());
        assertNull(player2.getCurrentMove());
    }

    @Test
    void compareMovesAndCalculateScore_player1CooperatesPlayer2Betrays() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setCurrentMove(Decision.COOPERER);
        player2.setCurrentMove(Decision.TRAHIR);

        String result = gameService.compareMovesAndCalculateScore(player1, player2);

        assertEquals(player1.getName() + " cooperated, " + player2.getName() + " betrayed. " + player2.getName() + " gets 5 points.", result);
        assertEquals(0, player1.getScore());
        assertEquals(5, player2.getScore());
    }

    @Test
    void processRoundEnd_incrementsRoundAndSavesPlayers() {
        // Préparation du jeu avec le round avant la fin et statut non terminé
        Game game = new Game();
        game.setRounds(5);
        game.setCurrentRound(4); // Un round avant la fin

        Player currentPlayer = new Player();
        currentPlayer.setName("Player1");
        currentPlayer.setCurrentMove(Decision.COOPERER);

        Player otherPlayer = new Player();
        otherPlayer.setName("Player2");
        otherPlayer.setCurrentMove(Decision.COOPERER);

        // Appel de la méthode processRoundEnd
        String result = gameService.processRoundEnd(game, currentPlayer, otherPlayer);

        // Vérifications après l'appel de la méthode
        assertEquals(5, game.getCurrentRound(), "Le round courant doit être incrémenté à 5");
        assertEquals("Game finished! Both players cooperated. Both get 3 points.", result);
        assertEquals(3, currentPlayer.getScore());
        assertEquals(3, otherPlayer.getScore());

        // Vérification que le statut du jeu n'est pas encore terminé
        assertEquals("FINISHED", game.getStatus());

        // Vérification des sauvegardes
        verify(gameRepository, times(1)).save(game);
        verify(playerRepository, times(1)).save(currentPlayer);
        verify(playerRepository, times(1)).save(otherPlayer);
    }

    @Test
    void processRoundEnd_finishesGameWhenRoundLimitReached() {
        // Préparation d'un jeu avec le dernier round atteint
        Game game = new Game();
        game.setRounds(5);
        game.setCurrentRound(5); // Dernier round atteint

        Player currentPlayer = new Player();
        currentPlayer.setName("Player1");
        currentPlayer.setCurrentMove(Decision.TRAHIR);

        Player otherPlayer = new Player();
        otherPlayer.setName("Player2");
        otherPlayer.setCurrentMove(Decision.COOPERER);

        // Appel de la méthode processRoundEnd
        String result = gameService.processRoundEnd(game, currentPlayer, otherPlayer);

        // Vérifications après l'appel de la méthode
        assertEquals("Game finished! Player1 betrayed, Player2 cooperated. Player1 gets 5 points.", result);
        assertEquals("FINISHED", game.getStatus(), "Le jeu doit être terminé après le dernier round");
        assertEquals(5, currentPlayer.getScore());
        assertEquals(0, otherPlayer.getScore());

        // Vérification des sauvegardes
        verify(gameRepository, times(1)).save(game);
        verify(playerRepository, times(1)).save(currentPlayer);
        verify(playerRepository, times(1)).save(otherPlayer);
    }
}
*/