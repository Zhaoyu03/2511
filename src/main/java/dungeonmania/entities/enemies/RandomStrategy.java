package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomStrategy implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        Position nextPos;
        GameMap map = game.getMap();
        Random randGen = new Random();
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
            map.moveTo(enemy, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(enemy, nextPos);
        }
        map.moveTo(enemy, nextPos);
    }

}
