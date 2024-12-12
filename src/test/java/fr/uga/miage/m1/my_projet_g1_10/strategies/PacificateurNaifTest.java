package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class PacificateurNaifTest {

    private PacificateurNaif strategy;

    @BeforeEach
    public void setUp() {
        strategy = new PacificateurNaif();
        strategy.setProbabiliteDeTrahir(0.3); // Temporarily increase probability for testing
    }

    @Test
     void testRandomlyBetray() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);

        boolean betrayed = false;
        for (int i = 0; i < 100; i++) {
            if (strategy.decider(opponentMoves) == Decision.TRAHIR) {
                betrayed = true;
                break;
            }
        }

        assertTrue(betrayed, "PacificateurNaif should betray with a small probability.");
    }
}
