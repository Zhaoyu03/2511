package dungeonmania.goals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import dungeonmania.goals.GoalStrategies.GoalAnd;
import dungeonmania.goals.GoalStrategies.GoalBoulders;
import dungeonmania.goals.GoalStrategies.GoalDestroy;
import dungeonmania.goals.GoalStrategies.GoalExit;
import dungeonmania.goals.GoalStrategies.GoalOr;
import dungeonmania.goals.GoalStrategies.GoalTreasure;
import dungeonmania.goals.GoalStrategies.Strategies;

public class GoalFactory {
    private static Map<String, Strategies> goalStrategies = new HashMap<String, Strategies>();
    static {
        goalStrategies.put("AND", new GoalAnd());
        goalStrategies.put("OR", new GoalOr());
        goalStrategies.put("exit", new GoalExit());
        goalStrategies.put("boulders", new GoalBoulders());
        goalStrategies.put("treasure", new GoalTreasure());
        goalStrategies.put("destroy", new GoalDestroy());
    }

    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        String strategieString = jsonGoal.getString("goal");
        return goalStrategies.get(strategieString).setGoal(jsonGoal, config);
    }
}
