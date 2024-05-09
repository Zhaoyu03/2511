package dungeonmania.entities.enemies;

import java.util.Random;

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

public class Assassin extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 8.0;
    public static final double DEFAULT_HEALTH = 15.0;
    public static final double DEFAULT_RATE = 0.3;
    public static final int DEFAULT_CONTROL_DURATION = 1;

    private int mindControlDuration = Assassin.DEFAULT_CONTROL_DURATION;
    private int bribeAmount = Assassin.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Assassin.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;
    private double failRate;
    private Random randGen;

    public Assassin(Position position, JSONObject config) {
        super(position);
        BattleStatistics battleStatistics;
        double health = config.optDouble("assassin_health", Assassin.DEFAULT_HEALTH);
        double attack = config.optDouble("assassin_attack", Assassin.DEFAULT_ATTACK);
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
        super.setBattleStatistics(battleStatistics);
        this.allyAttack = config.optDouble("ally_attack", Assassin.DEFAULT_HEALTH);
        this.allyDefence = config.optDouble("ally_defence", Assassin.DEFAULT_ATTACK);
        this.bribeAmount = config.optInt("assassin_bribe_amount", Assassin.DEFAULT_BRIBE_AMOUNT);
        this.bribeRadius = config.optInt("bribe_radius", Assassin.DEFAULT_BRIBE_RADIUS);
        this.failRate = config.optDouble("assassin_bribe_fail_rate", Assassin.DEFAULT_RATE);
        this.mindControlDuration = config.optInt("mind_control_duration", Assassin.DEFAULT_CONTROL_DURATION);
        this.randGen = new Random((int) (failRate * 100));
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

        // chance to fail when bribed.
        double randomNum = randGen.nextDouble();
        if (randomNum < failRate) {
            allied = false;
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
