package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SondeurRepentant implements IStrategie {

    // Utilisation de SecureRandom pour une génération aléatoire sécurisée et déclaration comme final
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;
    private boolean lastMoveWasTest = false;

    public Random getRandom() {
        return random;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
        // Coopérer par défaut si la liste est vide
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }

        Decision lastMove = opponent.get(opponent.size() - 1);

        // Si le dernier coup était un test, réagir en fonction du dernier coup de l'adversaire
        if (lastMoveWasTest) {
            if (lastMove == Decision.TRAHIR) {
                lastMoveWasTest = false;
                return Decision.COOPERER;
            }
            lastMoveWasTest = false;
        }

        // Trahir avec une probabilité définie et marquer le prochain coup comme un test
        if (random.nextDouble() < probabiliteDeTrahir) {
            lastMoveWasTest = true;
            return Decision.TRAHIR;
        }

        // Sinon, imiter la dernière décision de l'adversaire
        return lastMove;
    }
}
