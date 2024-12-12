package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DonnantPourDeuxDonnantAleatoire implements IStrategie {

    // Utilisation de SecureRandom pour une génération sécurisée
    private final Random random = new SecureRandom();
    private double randomProbability = 0.1;
    private Decision lastDecision = Decision.COOPERER;

    @Override
    public Decision decider(List<Decision> opponent) {
        // Génération d'une décision aléatoire avec une probabilité définie
        if (random.nextDouble() < randomProbability) {
            lastDecision = random.nextBoolean() ? Decision.COOPERER : Decision.TRAHIR;
            return lastDecision;
        }

        // Obtenir les deux derniers coups de l'adversaire
        Decision lastMove = opponent.get(opponent.size() - 1);
        Decision secondLastMove = opponent.get(opponent.size() - 2);

        // Si les deux derniers coups sont identiques, suivre ce coup
        if (lastMove == secondLastMove) {
            lastDecision = lastMove;
        }

        return lastDecision;
    }
}
