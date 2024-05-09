package dungeonmania.goals.achievedStrategies;

import dungeonmania.Game;
import dungeonmania.entities.Switch;
import dungeonmania.goals.Goal;

public class AchievedBoulders implements AchievedStrategies {
    @Override
    public boolean setAchieved(Game game, Goal goal1, Goal goal2, int target) {
        // TODO Auto-generated method stub
        return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }
}
