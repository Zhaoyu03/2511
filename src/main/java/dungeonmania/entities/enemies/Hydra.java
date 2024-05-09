package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONObject;
import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Hydra extends Enemy {
    public static final double DEFAULT_ATTACK = 2.0;
    public static final double DEFAULT_HEALTH = 8.0;
    public static final double DEFAULT_RATE = 0.3;
    public static final double DEFAULT_INCREASE = 2;
    private Random randGen = new Random();
    private double increaseRate;
    private double increaseAmount;
    private Random randGenHealth;
    private double currHealth;

    public Hydra(Position position, double health, double attack, JSONObject config) {
        super(position, health, attack);
        this.currHealth = health;
        this.increaseAmount = config.optDouble("hydra_health_increase_amount", Hydra.DEFAULT_INCREASE);
        this.increaseRate = config.optDouble("hydra_health_increase_rate", Hydra.DEFAULT_RATE);
        this.randGenHealth = new Random((int) (increaseRate * 100));
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            setMoveStrategy(new DefualtStrategy());
            getStrategy().move(game, this);
        } else {
            List<Position> pos = getPosition().getCardinallyAdjacentPositions();
            pos = pos.stream().filter(p -> map.canMoveTo(this, p)).collect(Collectors.toList());
            if (pos.size() == 0) {
                nextPos = getPosition();
            } else {
                nextPos = pos.get(randGen.nextInt(pos.size()));
            }
            game.getMap().moveTo(this, nextPos);
        }
    }

    public boolean isIncrease() {
        double randomNum = randGenHealth.nextDouble();
        return randomNum < increaseRate;
    }

    public double newHealth() {
        return currHealth + increaseAmount;
    }
}
