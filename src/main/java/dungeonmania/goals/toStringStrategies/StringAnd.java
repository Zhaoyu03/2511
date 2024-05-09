package dungeonmania.goals.toStringStrategies;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class StringAnd implements ToStringStrategies {
    @Override
    public String setString(Game game, Goal goal1, Goal goal2, boolean achieved) {
        // TODO Auto-generated method stub
        return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }
}
