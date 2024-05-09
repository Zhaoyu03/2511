package dungeonmania.goals.GoalStrategies;

import org.json.JSONObject;

import dungeonmania.goals.Goal;

public interface Strategies {
    public Goal setGoal(JSONObject jsonGoal, JSONObject config);
}
