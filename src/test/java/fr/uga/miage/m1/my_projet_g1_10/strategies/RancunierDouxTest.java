package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

class RancunierDouxTest {

    private RancunierDoux strategy;
    private List<Decision> opponentMoves;

    @BeforeEach
    void setUp() {
        strategy = new RancunierDoux();
        opponentMoves = new ArrayList<>();
    }

    @Test
    void testInitialCooperation() {
        // Si l'adversaire n'a pas encore joué, le premier coup doit être la coopération
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le premier coup doit être la coopération");
    }

    @Test
    void testStayInCooperationMode() {
        // Si l'adversaire coopère, le joueur doit continuer de coopérer
        opponentMoves.add(Decision.COOPERER);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decision, "Le joueur doit coopérer après une coopération de l'adversaire");
    }

    @Test
    void testEnterPunishmentModeAfterBetrayal() {
        // Si l'adversaire trahit, le joueur doit entrer en mode punition
        opponentMoves.add(Decision.TRAHIR);
        Decision decision = strategy.decider(opponentMoves);
        assertEquals(Decision.TRAHIR, decision, "Le joueur doit trahir en réponse à la première trahison de l'adversaire");
    }

    @Test
    void testPunishmentSequence() {
        // Simuler l'entrée en mode punition et vérifier la séquence t,t,t,t,c,c
        opponentMoves.add(Decision.TRAHIR); // L'adversaire trahit
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves), "Punition étape 1 : trahison");
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves), "Punition étape 2 : trahison");
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves), "Punition étape 3 : trahison");
        assertEquals(Decision.TRAHIR, strategy.decider(opponentMoves), "Punition étape 4 : trahison");
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves), "Punition étape 5 : coopération");
        assertEquals(Decision.COOPERER, strategy.decider(opponentMoves), "Punition étape 6 : coopération");
    }

    /*@Test
    void testReturnToCooperationAfterPunishment() {
        // Simuler une trahison suivie de la séquence complète de punition, puis vérifier le retour à la coopération
        opponentMoves.add(Decision.TRAHIR); // L'adversaire trahit
        strategy.decider(opponentMoves); // t
        strategy.decider(opponentMoves); // t
        strategy.decider(opponentMoves); // t
        strategy.decider(opponentMoves); // t
        strategy.decider(opponentMoves); // c
        strategy.decider(opponentMoves); // c

        // Vérifier que le joueur revient à la coopération
        Decision decisionAfterPunishment = strategy.decider(opponentMoves);
        assertEquals(Decision.COOPERER, decisionAfterPunishment, "Le joueur doit retourner à la coopération après la punition complète");
    }*/
}
