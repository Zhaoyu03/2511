package dungeonmania.goals.achievedStrategies;

import dungeonmania.Game;
import dungeonmania.goals.Goal;

public class AchievedTreasure implements AchievedStrategies {
    @Override
    public boolean setAchieved(Game game, Goal goal1, Goal goal2, int target) {
        // TODO Auto-generated method stub
        return game.getCollectedTreasureCount() >= target;
    }
}
