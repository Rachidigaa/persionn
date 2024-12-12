package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class VraiPacificateurTest {

    private VraiPacificateur strategy;

    @BeforeEach
    public void setUp() {
        strategy = new VraiPacificateur();
    }

    @Test
     void testCooperateIfNoConsecutiveBetrayals() {
        List<Decision> opponentMoves = List.of(Decision.COOPERER, Decision.TRAHIR, Decision.COOPERER);
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves), "Expected to cooperate when opponent hasn't betrayed twice in a row");
    }

    @Test
     void testRetaliateAfterConsecutiveBetrayals() {
        List<Decision> opponentMoves = List.of(Decision.TRAHIR, Decision.TRAHIR);
        Decision decision = strategy.decider(opponentMoves);
        assertTrue(decision == Decision.TRAHIR || decision == Decision.COOPERER,
                "Expected to either retaliate with betrayal or occasionally cooperate as a peace offering");

        if (decision == Decision.TRAHIR) {
            assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves), "Expected retaliation after two consecutive betrayals");
        }
    }

    @Test
     void testOccasionalPeaceOffering() {
        List<Decision> opponentMoves = List.of(Decision.TRAHIR, Decision.TRAHIR);
        int peaceOfferCount = 0;
        int trials = 1000;

        for (int i = 0; i < trials; i++) {
            if (strategy.decider(opponentMoves) == Decision.COOPERER) {
                peaceOfferCount++;
            }
        }

        double peaceOfferProbability = (double) peaceOfferCount / trials;
        assertTrue(peaceOfferProbability >= 0.15 && peaceOfferProbability <= 0.25, "Expected peace offer probability around 20%");
    }
}
