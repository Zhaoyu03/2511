package dungeonmania.goals.toStringStrategies;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class StringOr implements ToStringStrategies {
    @Override
    public String setString(Game game, Goal goal1, Goal goal2, boolean achieved) {
        // TODO Auto-generated method stub
        if (achieved)
            return "";
        else
            return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }
}
