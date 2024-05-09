package dungeonmania.entities.buildables;

import org.json.JSONObject;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Bow extends Buildable {
    private int durability;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    public Bow(JSONObject config) {
        super(null, config);
        this.durability = config.optInt("bow_durability");
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        JSONObject config = super.getConfig();
        int health = config.optInt("bow_health");
        int defence = config.optInt("bow_defence");
        int attack = config.optInt("bow_attack");
        int attackMangifier = config.optInt("bow_attackMangifier");
        int damageReducer = config.optInt("bow_damageReducer");
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMangifier, damageReducer));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
