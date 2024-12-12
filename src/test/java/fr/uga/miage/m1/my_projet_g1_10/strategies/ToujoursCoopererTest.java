package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ToujoursCoopererTest {

    private ToujoursCooperer strategy;

    @BeforeEach
    public void setUp() {
        strategy = new ToujoursCooperer();
    }

    @Test
     void testAlwaysCooperates() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);
        opponentMoves.add(Decision.TRAHIR);

        // Check multiple rounds of decisions to ensure it always cooperates
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves));
        assertEquals(Decision.COOPERER, strategy.decider(new ArrayList<>()));
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves));
    }
}
