package dungeonmania.entities.buildables;

import org.json.JSONObject;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Buildable extends Entity implements InventoryItem, BattleItem {
    private JSONObject config;

    public Buildable(Position position) {
        super(position);
    }

    public Buildable(Position position, JSONObject config) {
        super(null);
        this.config = config;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    public JSONObject getConfig() {
        return config;
    }
}
