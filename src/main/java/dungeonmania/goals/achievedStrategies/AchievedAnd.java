package dungeonmania.goals.achievedStrategies;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class AchievedAnd implements AchievedStrategies {
    @Override
    public boolean setAchieved(Game game, Goal goal1, Goal goal2, int target) {
        // TODO Auto-generated method stub
        return goal1.achieved(game) && goal2.achieved(game);
    }
}
