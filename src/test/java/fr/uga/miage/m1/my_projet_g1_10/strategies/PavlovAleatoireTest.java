package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PavlovAleatoireTest {
    private PavlovAleatoire strategie;

    @BeforeEach
    public void setUp() {
        strategie = new PavlovAleatoire();
    }

    @Test
    void testDecider_ChoixAleatoire() {
        // Pour tester le choix aléatoire, nous allons simuler plusieurs appels
        // On ne peut pas garantir un résultat exact ici, mais on peut vérifier que
        // la méthode retourne bien une décision valide

        // Simuler plusieurs appels pour vérifier le comportement aléatoire
        for (int i = 0; i < 100; i++) {
            Decision decision = strategie.decider(new ArrayList<>());

            // Vérifie que la décision est valide
            assertTrue(decision == Decision.COOPERER || decision == Decision.TRAHIR);
        }
    }

    @Test
    void testDecider_RépéterDernièreDécision_Score5() {
        strategie.setLastScore(5);
        strategie.decider(new ArrayList<>()); // Appel pour initialiser lastDecision

        Decision decision = strategie.decider(new ArrayList<>());
        assertEquals(strategie.getLastDecision(), decision); // Devrait répéter la dernière décision
    }

    @Test
    void testDecider_RépéterDernièreDécision_Score3() {
        strategie.setLastScore(3);
        strategie.decider(new ArrayList<>()); // Appel pour initialiser lastDecision

        Decision decision = strategie.decider(new ArrayList<>());
        assertEquals(strategie.getLastDecision(), decision); // Devrait répéter la dernière décision
    }

    @Test
    void testDecider_AlternerDécision() {
        // Teste l'alternance entre COOPERER et TRAHIR
        strategie.setLastScore(0);
        Decision firstDecision = strategie.decider(new ArrayList<>());
        Decision secondDecision = strategie.decider(new ArrayList<>());

        // Si la première décision était COOPERER, la seconde devrait être TRAHIR et vice versa
        if (firstDecision == Decision.COOPERER) {
            assertEquals(Decision.TRAHIR, secondDecision);
        } else {
            assertEquals(Decision.COOPERER, secondDecision);
        }
    }
}
