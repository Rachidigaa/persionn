package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraduelTest {
    private Graduel strategie;

    @BeforeEach
    public void setUp() {
        strategie = new Graduel();
    }

    @Test
    void testDecider_EmptyOpponentList() {
        // Teste le cas où la liste des décisions de l'adversaire est vide
        Decision decision = strategie.decider(Collections.emptyList());
        assertEquals(Decision.COOPERER, decision); // Doit commencer par coopérer
    }

    @Test
    void testDecider_OpponentBetraysOnce() {
        // Teste le cas où l'adversaire trahit une fois
        strategie.decider(Collections.singletonList(Decision.TRAHIR)); // L'adversaire trahit
        Decision decision = strategie.decider(Collections.singletonList(Decision.TRAHIR)); // Réaction à la trahison
        assertEquals(Decision.TRAHIR, decision); // Doit trahir en réponse
    }

    @Test
    void testDecider_OpponentBetraysMultipleTimes() {
        // Teste le cas où l'adversaire trahit plusieurs fois
        strategie.decider(Arrays.asList(Decision.TRAHIR, Decision.TRAHIR)); // L'adversaire trahit deux fois
        Decision decision = strategie.decider(Arrays.asList(Decision.TRAHIR, Decision.TRAHIR)); // Réaction à la trahison
        assertEquals(Decision.TRAHIR, decision); // Doit trahir en réponse
        decision = strategie.decider(Arrays.asList(Decision.TRAHIR, Decision.TRAHIR)); // Réaction à la trahison
        assertEquals(Decision.TRAHIR, decision); // Doit trahir encore
    }
}
