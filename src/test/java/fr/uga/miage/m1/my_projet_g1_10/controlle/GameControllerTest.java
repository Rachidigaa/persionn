package fr.uga.miage.m1.my_projet_g1_10.controlle;
import fr.uga.miage.m1.my_projet_g1_10.controller.GameController;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void createGame_createsGameSuccessfully() throws Exception {
        // Configuration de `mockGame` avec un `id` et des données valides
        Game mockGame = new Game();
        mockGame.setId(1L); // Assurez-vous que l'ID est défini
        mockGame.setRounds(5);
        mockGame.setStatus("WAITING");

        when(gameService.createGame("Player1", 5)).thenReturn(mockGame);

        // Exécution du test et vérification du statut et du contenu
        mockMvc.perform(post("/game/create")
                        .param("playerName", "Player1")
                        .param("rounds", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))          // Vérifie que l'ID n'est pas null
                .andExpect(jsonPath("$.rounds").value(5))       // Vérifie que le nombre de rounds est correct
                .andExpect(jsonPath("$.status").value("WAITING")); // Vérifie que le statut est correct
    }


    @Test
    void joinGame_joinsGameSuccessfully() throws Exception {
        // Configuration de `mockGame` avec des données valides
        Game mockGame = new Game();
        mockGame.setId(1L);
        mockGame.setRounds(5);

        when(gameService.joinGame(anyLong(), eq("NewPlayer"))).thenReturn(Optional.of(mockGame));

        // Exécution du test
        mockMvc.perform(post("/game/join")
                        .param("gameId", "1")
                        .param("playerName", "NewPlayer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rounds").value(5));
    }

    @Test
    void joinGame_gameNotFound() throws Exception {
        // Configuration du mock pour simuler un jeu introuvable
        when(gameService.joinGame(anyLong(), eq("NewPlayer"))).thenReturn(Optional.empty());

        // Exécution du test et vérification du statut 204 No Content
        mockMvc.perform(post("/game/join")
                        .param("gameId", "1")
                        .param("playerName", "NewPlayer"))
                .andExpect(status().isNoContent());
    }


    @Test
    void playRound_playsRoundSuccessfully() throws Exception {
        // Configuration du service mocké pour retourner un message de résultat
        when(gameService.playRound(anyLong(), anyLong(), eq("COOPERER")));

        // Exécution du test et vérification du statut et du contenu
        mockMvc.perform(post("/game/play")
                        .param("gameId", "1")
                        .param("playerId", "1")
                        .param("move", "COOPERER"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player1 cooperated."));
    }

    @Test
    void quittePartie_quitsGameSuccessfully() throws Exception {
        // Configuration du service mocké pour retourner un message de confirmation
        when(gameService.quitterPartie(anyLong(), eq("TOUJOURSTRAHIR"))).thenReturn("Joueur a quitté La partie");

        // Exécution du test et vérification du statut et du contenu
        mockMvc.perform(post("/game/quitte")
                        .param("playerId", "1")
                        .param("strategie", "TOUJOURSTRAHIR"))
                .andExpect(status().isOk())
                .andExpect(content().string("Joueur a quitté La partie"));
    }
}

