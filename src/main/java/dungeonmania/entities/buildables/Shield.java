package dungeonmania.entities.buildables;

import org.json.JSONObject;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Shield extends Buildable {
    private int durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
    }

    public Shield(JSONObject config) {
        super(null, config);
        this.durability = config.optInt("shield_durability");
        this.defence = config.optInt("shield_defence");
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
        int health = config.optInt("shield_health");
        int attack = config.optInt("shield_attack");
        int attackMangifier = config.optInt("shield_attackMangifier");
        int damageReducer = config.optInt("shield_damageReducer");
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(health, attack, defence, attackMangifier, damageReducer));
    }

    @Override
    public int getDurability() {
        return durability;
    }

}
