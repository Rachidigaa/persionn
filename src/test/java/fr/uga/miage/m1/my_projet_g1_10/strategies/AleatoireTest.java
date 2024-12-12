package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AleatoireTest {

    private Aleatoire strategy;

    @BeforeEach
    public void setUp() {
        strategy = new Aleatoire();
    }

    @Test
    void testRandomDecision() {
        Set<Decision> decisions = new HashSet<>();

        // Appelons la méthode plusieurs fois pour capturer les deux décisions possibles
        for (int i = 0; i < 100; i++) {
            Decision decision = strategy.decider(null);
            decisions.add(decision);
        }

        // Vérifiez que les deux décisions sont présentes dans l'ensemble
        assertTrue(decisions.contains(Decision.TRAHIR), "La décision TRAHIR devrait être possible.");
        assertTrue(decisions.contains(Decision.COOPERER), "La décision COOPERER devrait être possible.");
    }
}
