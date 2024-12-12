package fr.uga.miage.m1.my_projet_g1_10.model;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = "WAITING"; // WAITING, STARTED, FINISHED
    private int rounds; // Nombre de tours total
    private int currentRound = 0; // Tours joués

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Player> getPlayers() { return players; }

    public void addPlayer(Player player){
        this.players.add(player);
    }
    public void setPlayers(List<Player> players) { this.players = players; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getRounds() { return rounds; }
    public void setRounds(int rounds) { this.rounds = rounds; }

    public int getCurrentRound() { return currentRound; }
    public void setCurrentRound(int currentRound) { this.currentRound = currentRound; }

    public boolean isFull() { return players.size() == 2; }

    public boolean isFinished() {
        return currentRound >= rounds;
    }

    public void incrementRound() {
        this.currentRound++;
        if (this.currentRound >= this.rounds) {
            this.status = "FINISHED";
        }
    }
}