package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class NoMoveEntity extends Entity {
    public NoMoveEntity(Position position) {
        super(position);
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

}
