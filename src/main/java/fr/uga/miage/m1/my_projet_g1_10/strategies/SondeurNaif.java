package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class SondeurNaif implements IStrategie {

    // Utilisation de SecureRandom pour une génération sécurisée et réutilisation de l'instance
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;

    @Override
    public Decision decider(List<Decision> opponent) {
        // Si la liste est vide, coopérer par défaut
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }

        // Avec une petite probabilité, trahir
        if (random.nextDouble() < probabiliteDeTrahir) {
            return Decision.TRAHIR;
        }

        // Sinon, renvoyer la dernière décision de l'adversaire
        return opponent.get(opponent.size() - 1);
    }
}
