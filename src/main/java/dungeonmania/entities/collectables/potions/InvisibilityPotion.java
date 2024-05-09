package dungeonmania.entities.collectables.potions;

import org.json.JSONObject;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvisibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    public InvisibilityPotion(Position position, JSONObject config) {
        super(position, config);
        int duration = config.optInt("invisibility_potion_duration", InvisibilityPotion.DEFAULT_DURATION);
        super.setDuration(duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        JSONObject config = super.getConfig();
        int health = config.optInt("invisibility_potion_health");
        int defence = config.optInt("invisibility_potion_defence");
        int attack = config.optInt("invisibility_potion_attack");
        int attackMangifier = config.optInt("invisibility_potion_attackMangifier");
        int damageReducer = config.optInt("invisibility_potion_damageReducer");
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMangifier, damageReducer, false, false));
    }
}
