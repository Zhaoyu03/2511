package dungeonmania.entities.buildables;

import org.json.JSONObject;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class Sceptre extends Buildable {
    private int durability;
    private int duration;
    private JSONObject config;

    public Sceptre(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    public Sceptre(Position position, JSONObject config) {
        super(position);
        this.config = config;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Sceptre(JSONObject config) {
        super(null, config);
        config.optInt("mind_control_duration");
    }

    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 0, 0));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
