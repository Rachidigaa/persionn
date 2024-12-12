package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.util.List;

public class Graduel implements IStrategie {

    private int betrayCount = 0;
    private int remainingBetrayals = 0;
    private int cooperativePhase = 0;  // Track additional cooperations after betrayals

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.isEmpty()) {
            return Decision.COOPERER;  // Start by cooperating
        }

        Decision lastOpponentMove = opponent.get(opponent.size() - 1);

        // Track opponent betrayal
        if (lastOpponentMove == Decision.TRAHIR) {
            betrayCount++;
            remainingBetrayals = betrayCount;  // Set remaining betrayals based on total opponent betrayals
            cooperativePhase = 0;  // Reset cooperation phase after a betrayal is detected
        }

        // Execute required betrayals if any remaining
        if (remainingBetrayals > 0) {
            remainingBetrayals--;
            return Decision.TRAHIR;
        }

        // After betrayals, return to cooperation for two turns
        if (cooperativePhase < 2) {
            cooperativePhase++;
            return Decision.COOPERER;
        }

        // Default to cooperating when no betrayals or retaliation needed
        return Decision.COOPERER;
    }
}