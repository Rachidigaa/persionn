package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PacificateurNaif implements IStrategie {
    // Utilisation de SecureRandom pour des raisons de sécurité
    private final Random random = new SecureRandom();
    private double probabiliteDeTrahir = 0.1;

    public void setProbabiliteDeTrahir(Double probabilite) {
        this.probabiliteDeTrahir = probabilite;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
        // Générer une décision aléatoire pour trahir avec une petite probabilité
        if (random.nextDouble() < probabiliteDeTrahir) {
            return Decision.TRAHIR;
        }
        return Decision.COOPERER;
    }
}
