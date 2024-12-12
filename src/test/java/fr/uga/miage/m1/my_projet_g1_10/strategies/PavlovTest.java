package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class PavlovTest {

    private Pavlov strategy;

    @BeforeEach
    public void setUp() {
        strategy = new Pavlov();
    }

    @Test
     void testRepeatLastChoiceOnHighScore() {
        strategy.setLastScore(5);
        strategy.decider(null);  // Should repeat last decision
        assertEquals(Decision.COOPERER, strategy.decider(null));
    }

    @Test
     void testAlternateOnLowScore() {
        strategy.setLastScore(1);
        assertEquals(Decision.TRAHIR, strategy.decider(null));
    }
}
