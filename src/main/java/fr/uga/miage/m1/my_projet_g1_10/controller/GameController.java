package fr.uga.miage.m1.my_projet_g1_10.controller;

import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/create")
    public Game createGame(@RequestParam String playerName, @RequestParam int rounds) {
        return gameService.createGame(playerName, rounds);
    }


    @PostMapping("/join")
    public ResponseEntity<Game> joinGame(@RequestParam Long gameId, @RequestParam String playerName) {
        Optional<Game> game = gameService.joinGame(gameId, playerName);
        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }


    // incrémenter le tour et vérifier si la partie est terminée
    @PostMapping("/play")
    public ResponseEntity<Map<String, Object>> playRound(
            @RequestParam Long gameId,
            @RequestParam Long playerId,
            @RequestParam String move
    ) {
        Map<String, Object> response = gameService.playRound(gameId, playerId, move);
        return ResponseEntity.ok(response);
    }




    @PostMapping("/quitte")
    public ResponseEntity<Map<String, String>> quittePartie(
            @RequestParam Long playerId,
            @RequestParam String strategie
    ) {
        String result = gameService.quitterPartie(playerId, strategie);
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{gameId}/status")
    public ResponseEntity<Map<String, Object>> getGameStatus(@PathVariable Long gameId) {
        System.out.println("im here");
        Map<String, Object> gameStatus = gameService.getGameStatus(gameId);
        if (gameStatus != null) {
            return ResponseEntity.ok(gameStatus);
        }
        return ResponseEntity.notFound().build(); // Retourne 404 si le jeu n'existe pas
    }

    @GetMapping("/{gameId}/score")
    public ResponseEntity<Map<String, Object>> getGameScore(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getGameScore(gameId));
    }

    @GetMapping("/strategies")
    public ResponseEntity<Strategie[]> getStrategies() {
        return ResponseEntity.ok(Strategie.values());
    }







}
