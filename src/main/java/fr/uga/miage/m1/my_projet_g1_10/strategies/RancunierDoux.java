package fr.uga.miage.m1.my_projet_g1_10.strategies;

import fr.uga.miage.m1.my_projet_g1_10.enums.Decision;
import java.util.List;

public class RancunierDoux implements IStrategie {

    private boolean punishmentMode = false;
    private int punishmentStep = 0;

    @Override
    public Decision decider(List<Decision> opponent) {
        if (opponent.isEmpty()) {
            return Decision.COOPERER; // Start by cooperating
        }

        Decision lastOpponentMove = opponent.get(opponent.size() - 1);

        // Trigger punishment mode if opponent betrays and we are not already punishing
        if (lastOpponentMove == Decision.TRAHIR && !punishmentMode) {
            punishmentMode = true;
            punishmentStep = 0;  // Reset punishment step
        }

        // If in punishment mode, follow the sequence of t,t,t,t,c,c
        if (punishmentMode) {
            Decision currentDecision = (punishmentStep < 4) ? Decision.TRAHIR : Decision.COOPERER; // First 4 steps are betrayals, then 2 cooperations
            punishmentStep++;

            // End punishment mode after completing the sequence
            if (punishmentStep >= 6) {
                punishmentMode = false;
            }
            return currentDecision;
        }

        // Default to cooperation if not in punishment mode
        return Decision.COOPERER;
    }
}
