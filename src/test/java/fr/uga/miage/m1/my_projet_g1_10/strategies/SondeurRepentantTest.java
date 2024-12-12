package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SondeurRepentantTest {

    private SondeurRepentant strategy;
    private List<Decision> opponentMoves;

    @BeforeEach
    void setUp() {
        strategy = new SondeurRepentant();
        opponentMoves = new ArrayList<>();
    }

    @Test
    void testInitialCooperation() {
        // Si l'adversaire n'a pas encore joué, le premier coup doit être la coopération
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le premier coup doit être la coopération");
    }

    @Test
    void testStayInCooperationModeAfterOpponentCooperation() {
        // L'adversaire coopère, donc le joueur doit imiter et coopérer également
        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le joueur doit coopérer après une coopération de l'adversaire");
    }

    /*
    @Test
    void testEnterTestModeAndRepentOnOpponentBetrayal() {
        // Entrer en mode test en trahissant, puis retourner à la coopération si l'adversaire trahit
        opponentMoves.add(Decision.COOPERER);  // Coopération de l'adversaire pour initier un test
        strategy.decider(opponentMoves);       // Test avec une trahison

        opponentMoves.add(Decision.TRAHIR);    // L'adversaire trahit en réponse
        Decision decision = strategy.decider(opponentMoves);

        assertEquals(Decision.COOPERER, decision, "Après une trahison de l'adversaire, le joueur doit se repentir en coopérant");
    }*/

    /*@Test
    void testProbabilisticBetrayal() {
        // Simuler la trahison avec probabilité en utilisant un mock pour le Random
        SondeurRepentant strategyWithMockedRandom = spy(strategy);
        doReturn(0.05).when(strategyWithMockedRandom).getRandom().nextDouble();

        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategyWithMockedRandom.decider(opponentMoves);

        assertEquals(Decision.TRAHIR, decision, "Le joueur doit trahir lorsque le tirage aléatoire est en dessous de la probabilité de trahison");
    }*/

    @Test
    void testImitationOfLastMove() {
        // Si l'adversaire a trahi en dernier et qu'il n'y a pas de test en cours, le joueur doit imiter
        opponentMoves.add(Decision.TRAHIR);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.TRAHIR, decision, "Le joueur doit imiter la dernière décision de l'adversaire");
    }
}
