package dungeonmania.goals.toStringStrategies;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public interface ToStringStrategies {
    public String setString(Game game, Goal goal1, Goal goal2, boolean achieved);
}
