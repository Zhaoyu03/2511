package dungeonmania.entities.buildables;

import org.json.JSONObject;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    private int attack;
    private int defence;
    private int durability;

    public MidnightArmour(JSONObject config) {
        super(null, config);
        this.attack = config.optInt("midnight_armour_attack");
        this.defence = config.optInt("midnight_armour_defence");
        //TODO Auto-generated constructor stub
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        // TODO Auto-generated method stub
        JSONObject config = super.getConfig();
        return BattleStatistics.applyBuff(origin,
                new BattleStatistics(0, attack, defence, 0, 0));
    }

    @Override
    public void use(Game game) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getDurability() {
        // TODO Auto-generated method stub
        return durability;
    }

}
