package dungeonmania.entities.enemies;

import org.json.JSONObject;
import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    public static final int DEFAULT_CONTROL_DURATION = 1;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;
    private int mindControlDuration = Mercenary.DEFAULT_CONTROL_DURATION;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    // Corrected a problem with too many parameters
    public Mercenary(Position position, JSONObject config) {
        super(position);
        BattleStatistics battleStatistics;
        double health = config.optDouble("mercenary_health", Mercenary.DEFAULT_HEALTH);
        double attack = config.optDouble("mercenary_attack", Mercenary.DEFAULT_ATTACK);
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
        super.setBattleStatistics(battleStatistics);
        this.allyAttack = config.optDouble("ally_attack", Mercenary.DEFAULT_HEALTH);
        this.allyDefence = config.optDouble("ally_defence", Mercenary.DEFAULT_ATTACK);
        this.bribeAmount = config.optInt("bribe_amount", Mercenary.DEFAULT_BRIBE_AMOUNT);
        this.bribeRadius = config.optInt("bribe_radius", Mercenary.DEFAULT_BRIBE_RADIUS);
        this.mindControlDuration = config.optInt("mind_control_duration", Mercenary.DEFAULT_CONTROL_DURATION);
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        if (player.hasSceptre())
            return true;
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    private void mindcontrol(Player player, Game game) {
        for (int i = 0; i < mindControlDuration; i++) {
            game.tick();
        }
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        if (player.hasSceptre()) {
            mindcontrol(player, game);
            allied = false;
        } else {
            bribe(player);
        }
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        if (allied) {
            nextPos = isAdjacentToPlayer ? player.getPreviousDistinctPosition()
                    : map.dijkstraPathFind(getPosition(), player.getPosition(), this);
            if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), nextPos))
                isAdjacentToPlayer = true;
            map.moveTo(this, nextPos);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            // Move random
            setMoveStrategy(new RandomStrategy());
            getStrategy().move(game, this);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            setMoveStrategy(new DefualtStrategy());
            getStrategy().move(game, this);
        } else {
            // Follow hostile
            setMoveStrategy(new FollowHostileStrategy());
            getStrategy().move(game, this);
        }

    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }
}
