package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.util.List;

public class Pavlov implements IStrategie {

    private Decision lastDecision = Decision.COOPERER;
    private int lastScore = 0;

    public void setLastScore(int score) {
        this.lastScore = score;
    }

    @Override
    public Decision decider(List<Decision> opponent) {
        if (lastScore == 5 || lastScore == 3) {
            return lastDecision;  // Repeat last choice
        } else {
            // Alternate choice if last score was neither 5 nor 3
            lastDecision = (lastDecision == Decision.COOPERER) ? Decision.TRAHIR : Decision.COOPERER;
            return lastDecision;
        }
    }
}
