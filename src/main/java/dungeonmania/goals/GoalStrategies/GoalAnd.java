package dungeonmania.goals.GoalStrategies;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;

public class GoalAnd implements Strategies {
    @Override
    public Goal setGoal(JSONObject jsonGoal, JSONObject config) {
        // TODO Auto-generated method stub
        JSONArray subgoals;
        subgoals = jsonGoal.getJSONArray("subgoals");
        return new Goal("AND", GoalFactory.createGoal(subgoals.getJSONObject(0), config),
                GoalFactory.createGoal(subgoals.getJSONObject(1), config));
    }
}
