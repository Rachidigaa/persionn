package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.util.List;

public class Adaptatif implements IStrategie {

    private final List<Decision> initialMoves = List.of(
            Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER, Decision.COOPERER,
            Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR, Decision.TRAHIR
    );
    private int currentMove = 0;
    private int scoreCooperate = 0;
    private int scoreBetray = 0;
    private int totalCooperate = 0;
    private int totalBetray = 0;

    public void updateScore(int score, Decision lastDecision) {
        if (lastDecision == Decision.COOPERER) {
            scoreCooperate += score;
            totalCooperate++;
        } else {
            scoreBetray += score;
            totalBetray++;
        }
    }

    @Override
    public Decision decider(List<Decision> opponent) {
        if (currentMove < initialMoves.size()) {
            return initialMoves.get(currentMove++);
        }

        double avgCooperate = (totalCooperate > 0) ? (double) scoreCooperate / totalCooperate : 0;
        double avgBetray = (totalBetray > 0) ? (double) scoreBetray / totalBetray : 0;

        return avgBetray > avgCooperate ? Decision.TRAHIR : Decision.COOPERER;
    }
}
