package dungeonmania.entities.collectables.potions;

import org.json.JSONObject;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvincibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    public InvincibilityPotion(Position position, JSONObject config) {
        super(position, config);
        int duration = config.optInt("invincibility_potion_duration", InvincibilityPotion.DEFAULT_DURATION);
        super.setDuration(duration);
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        JSONObject config = super.getConfig();
        int health = config.optInt("invincibility_potion_health");
        int defence = config.optInt("invincibility_potion_defence");
        int attack = config.optInt("invincibility_potion_attack");
        int attackMangifier = config.optInt("invincibility_potion_attackMangifier");
        int damageReducer = config.optInt("invincibility_potion_damageReducer");
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMangifier, damageReducer, true, true));
    }
}
