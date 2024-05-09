package dungeonmania.goals;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.Game;
import dungeonmania.goals.achievedStrategies.AchievedAnd;
import dungeonmania.goals.achievedStrategies.AchievedBoulders;
import dungeonmania.goals.achievedStrategies.AchievedDestroy;
import dungeonmania.goals.achievedStrategies.AchievedExit;
import dungeonmania.goals.achievedStrategies.AchievedOr;
import dungeonmania.goals.achievedStrategies.AchievedStrategies;
import dungeonmania.goals.achievedStrategies.AchievedTreasure;
import dungeonmania.goals.toStringStrategies.StringAnd;
import dungeonmania.goals.toStringStrategies.StringBoulders;
import dungeonmania.goals.toStringStrategies.StringDestroy;
import dungeonmania.goals.toStringStrategies.StringExit;
import dungeonmania.goals.toStringStrategies.StringOr;
import dungeonmania.goals.toStringStrategies.StringTreasure;
import dungeonmania.goals.toStringStrategies.ToStringStrategies;

public class Goal {
    private String type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    private static Map<String, AchievedStrategies> achievedCheck = new HashMap<String, AchievedStrategies>();
    static {
        achievedCheck.put("AND", new AchievedAnd());
        achievedCheck.put("OR", new AchievedOr());
        achievedCheck.put("exit", new AchievedExit());
        achievedCheck.put("boulders", new AchievedBoulders());
        achievedCheck.put("treasure", new AchievedTreasure());
        achievedCheck.put("destroy", new AchievedDestroy());
    }

    private static Map<String, ToStringStrategies> toStringCheck = new HashMap<String, ToStringStrategies>();
    static {
        toStringCheck.put("AND", new StringAnd());
        toStringCheck.put("OR", new StringOr());
        toStringCheck.put("exit", new StringExit());
        toStringCheck.put("boulders", new StringBoulders());
        toStringCheck.put("treasure", new StringTreasure());
        toStringCheck.put("destroy", new StringDestroy());
    }

    public Goal(String type) {
        this.type = type;
    }

    public Goal(String type, int target) {
        this.type = type;
        this.target = target;
    }

    public Goal(String type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;
        return achievedCheck.get(type).setAchieved(game, goal1, goal2, target);
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return toStringCheck.get(type).setString(game, goal1, goal2, achieved(game));
    }

}
