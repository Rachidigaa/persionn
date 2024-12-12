package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;

import java.util.List;

public class DonnantDonnant implements IStrategie {
    @Override
    public Decision decider(List<Decision> opponent){
        if (opponent.isEmpty()) {
            return Decision.COOPERER;
        }
        Decision lastMove = opponent.get(opponent.size() - 1);
        return lastMove.equals(Decision.COOPERER) ? Decision.COOPERER : Decision.TRAHIR;

    }
}
