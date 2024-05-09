package dungeonmania.entities.enemies;

import dungeonmania.Game;

public interface MoveStrategy {
    public void move(Game game, Enemy enemy);

}
