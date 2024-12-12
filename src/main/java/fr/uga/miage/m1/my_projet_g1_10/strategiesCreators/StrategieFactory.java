package fr.uga.miage.m1.my_projet_g1_10.strategiesCreators;

import fr.uga.miage.m1.my_projet_g1_10.strategies.*;


public class StrategieFactory {

    private StrategieFactory() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static IStrategie getStrategie(fr.uga.miage.m1.my_projet_g1_10.enums.Strategie strategieEnum) {
        switch (strategieEnum) {
            case DONNANTDONNANT:
                return new DonnantDonnant();
            case RANCUNIER:
                return new Rancunier();
            case TOUJOURSCOOPERER:
                return new ToujoursCooperer();
            case TOUJOURSTRAHIR:
                return new ToujoursTrahir();
            case ALEATOIRE:
                return new Aleatoire();
            case DONNANTDONNANTALEATOIRE:
                return new DonnantDonnantAleatoire();
            case DONNANTPOURDEUXDONNANTALEATOIRE:
                return new DonnantPourDeuxDonnantAleatoire();
            case DONNANTDONNANTSOUPCONNEUX:
                return new DonnantDonnantSoupconneux();
            case DONNANTPOURDEUXDONNANT:
                return new DonnantPourDeuxDonnant();
            case PACIFICATEURNAIF:
                return new PacificateurNaif();
            case SONDEURNAIF:
                return new SondeurNaif();
            case SONDEURREPENTANT:
                return new SondeurRepentant();
            case GRADUEL:
                return new Graduel();
            default:
                throw new IllegalArgumentException("Stratégie non reconnue : " + strategieEnum);
        }
    }
}


