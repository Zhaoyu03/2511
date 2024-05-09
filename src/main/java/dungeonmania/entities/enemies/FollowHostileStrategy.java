package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class FollowHostileStrategy implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        nextPos = map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
        map.moveTo(enemy, nextPos);
    }
}
