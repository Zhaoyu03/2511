package dungeonmania.entities.collectables;

import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public class Treasure extends CollectableEntities implements InventoryItem {
    public Treasure(Position position) {
        super(position);
    }
}
