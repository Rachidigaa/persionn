package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.util.List;

public class DonnantPourDeuxDonnant implements IStrategie {

    private Decision lastDecision = Decision.COOPERER;

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.size() >= 2) {
            Decision lastMove = opponent.get(opponent.size() - 1);
            Decision secondLastMove = opponent.get(opponent.size() - 2);

            if (lastMove == secondLastMove) {
                lastDecision = lastMove;
            }
        }

        return lastDecision;
    }
}
