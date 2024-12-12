package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DonnantDonnantAleatoireTest {

    private DonnantDonnantAleatoire strategy;

    @BeforeEach
    void setUp() {
        strategy = new DonnantDonnantAleatoire();
    }

    @Test
    void testFollowsOpponentLastMoveWithRandomness() {
        List<Decision> opponentMoves = new ArrayList<>();
        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategy.decider(opponentMoves);
        assertTrue(decision == Decision.COOPERER || decision == Decision.TRAHIR);
    }
}
