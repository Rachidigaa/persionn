package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class RancunierTest {

    private Rancunier strategy;

    @BeforeEach
    public void setUp() {
        strategy = new Rancunier();
    }

    @Test
     void testCooperateUntilBetrayed() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);
        opponentMoves.add(Decision.COOPERER);
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves));

        opponentMoves.add(Decision.TRAHIR);
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));
    }

    @Test
     void testAlwaysBetrayAfterBetrayed() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.TRAHIR);
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));

        opponentMoves.add(Decision.COOPERER);
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));
    }
}
