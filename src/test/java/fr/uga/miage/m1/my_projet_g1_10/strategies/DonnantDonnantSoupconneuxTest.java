package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonnantDonnantSoupconneuxTest {

    private DonnantDonnantSoupconneux strategy;

    @BeforeEach
    public void setUp() {
        strategy = new DonnantDonnantSoupconneux();
    }

    @Test
    void testStartsWithBetray() {
        List<Decision> opponentMoves = new ArrayList<>();
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves));
    }

    @Test
    void testReciprocatesAfterInitialBetray() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.TRAHIR);
        opponentMoves.add(Decision.COOPERER);
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves));
    }
}
