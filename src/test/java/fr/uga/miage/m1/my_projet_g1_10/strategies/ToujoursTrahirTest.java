package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ToujoursTrahirTest {

    private ToujoursTrahir strategy;

    @BeforeEach
    public void setUp() {
        strategy = new ToujoursTrahir();
    }

    @Test
     void testAlwaysBetrays() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);
        opponentMoves.add(Decision.TRAHIR);

        // Check multiple rounds of decisions to ensure it always betrays
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));
        assertEquals(Decision.TRAHIR, strategy.decider(new ArrayList<>()));
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));
    }
}
