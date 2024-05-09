package dungeonmania.goals.achievedStrategies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.goals.Goal;
import dungeonmania.util.Position;

public class AchievedExit implements AchievedStrategies {
    @Override
    public boolean setAchieved(Game game, Goal goal1, Goal goal2, int target) {
        // TODO Auto-generated method stub
        Player character = game.getPlayer();
        Position pos = character.getPosition();
        List<Exit> es = game.getMap().getEntities(Exit.class);
        if (es == null || es.size() == 0)
            return false;
        return es.stream().map(Entity::getPosition).anyMatch(pos::equals);
    }
}
