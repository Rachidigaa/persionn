package fr.uga.miage.m1.my_projet_g1_10.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int score = 0;  // Champ pour le score
    private Decision currentMove;    // Stocke le choix du joueur pour le tour actuel ("cooperate" ou "betray")

    private Strategie strategie;

    private Boolean present = true;
    @ElementCollection
    private List<Decision> moveHistory=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void incrementScore(int points) { this.score += points; }  // Méthode pour incrémenter le score

    public Decision getCurrentMove() { return currentMove; }
    public void setCurrentMove(Decision move) { this.currentMove = move; }  // Méthode pour définir le move

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public List<Decision> getMoveHistory(){
        return moveHistory;
    }
    public Decision getLastMove() {
        if (!moveHistory.isEmpty()) {
            return moveHistory.get(moveHistory.size() - 1);  // Retourne le dernier élément de moveHistory
        } else {
            return null;  // Retourne null si aucun mouvement n'a été fait
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    public void changePresent(){
        this.present = !this.present;
    }

    public Boolean getPresent() {
        return present;
    }

    public void addMoves(Decision decision){
        this.moveHistory.add(decision);
    }

    public void setMoveHistory(List<Decision> moveHistory) {
        this.moveHistory = moveHistory;
    }
}
