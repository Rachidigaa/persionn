package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class VraiPacificateur implements IStrategie {

    // Utilisation de SecureRandom pour une génération sécurisée et réutilisation de l'instance
    private final Random random = new SecureRandom();
    private  static final double PEACE_OFFER_PROBABILITY = 0.2;  // 20% chance to make peace after betrayal
    private boolean lastTwoMovesWereBetrayals = false;

    @Override
    public Decision decider(List<Decision> opponent) {
        // Vérifie si l'adversaire a trahi deux fois de suite
        if (opponent.size() >= 2) {
            Decision lastMove = opponent.get(opponent.size() - 1);
            Decision secondLastMove = opponent.get(opponent.size() - 2);

            lastTwoMovesWereBetrayals = (lastMove == Decision.TRAHIR && secondLastMove == Decision.TRAHIR);
        }

        // Si l'adversaire a trahi deux fois consécutivement, répondre par une trahison
        if (lastTwoMovesWereBetrayals) {
            // Offrir la paix occasionnellement en coopérant
            if (random.nextDouble() < PEACE_OFFER_PROBABILITY) {
                lastTwoMovesWereBetrayals = false;  // Réinitialise le flag après l'offre de paix
                return Decision.COOPERER;
            }
            return Decision.TRAHIR;
        }

        // Comportement par défaut est de coopérer
        return Decision.COOPERER;
    }
}
