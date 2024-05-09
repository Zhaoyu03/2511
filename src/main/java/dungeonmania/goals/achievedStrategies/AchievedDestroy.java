package dungeonmania.goals.achievedStrategies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.goals.Goal;

public class AchievedDestroy implements AchievedStrategies {
    public boolean setAchieved(Game game, Goal goal1, Goal goal2, int target) {
        // TODO Auto-generated method stub
        List<ZombieToastSpawner> spawner = game.getMap().getEntities(ZombieToastSpawner.class);
        boolean isNOSpawner = spawner.size() == 0 || spawner == null;
        return game.getKill() >= target && isNOSpawner;
    }
}
