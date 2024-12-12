package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdaptatifTest {

    private Adaptatif strategy;

    @BeforeEach
    public void setUp() {
        strategy = new Adaptatif();
    }

    @Test
    void testInitialSequence() {
        // Vérifier que la séquence initiale est bien suivie
        List<Decision> expectedMoves = List.of(
                Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
                Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR
        );

        for (int i = 0; i < expectedMoves.size(); i++) {
            assertEquals(expectedMoves.get(i), strategy.decider(null), "Move " + i + " should be " + expectedMoves.get(i));
        }
    }

    @Test
    void testAdaptativeBehaviorAfterInitialSequenceWithMoreBetrayScore() {
        // Simuler des scores pour tester le comportement adaptatif
        for (int i = 0; i < 11; i++) {
            strategy.decider(null); // Passer la séquence initiale
        }

        strategy.updateScore(2, Decision.COOPERER);
        strategy.updateScore(5, Decision.TRAHIR);

        // Dans ce cas, le score de trahison est plus élevé, donc la stratégie devrait choisir TRAHIR
        assertEquals(Decision.TRAHIR, strategy.decider(null));
    }

    @Test
    void testAdaptativeBehaviorAfterInitialSequenceWithMoreCooperateScore() {
        // Simuler des scores pour tester le comportement adaptatif
        for (int i = 0; i < 11; i++) {
            strategy.decider(null); // Passer la séquence initiale
        }

        strategy.updateScore(5, Decision.COOPERER);
        strategy.updateScore(2, Decision.TRAHIR);

        // Dans ce cas, le score de coopération est plus élevé, donc la stratégie devrait choisir COOPERER
        assertEquals(Decision.COOPERER, strategy.decider(null));
    }

    @Test
    void testAdaptativeBehaviorWithNoScore() {
        // Passer la séquence initiale
        for (int i = 0; i < 11; i++) {
            strategy.decider(null);
        }

        // Sans score, le comportement par défaut devrait être de coopérer
        assertEquals(Decision.COOPERER, strategy.decider(null));
    }
}
