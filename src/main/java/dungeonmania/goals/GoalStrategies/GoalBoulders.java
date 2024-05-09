package dungeonmania.goals.GoalStrategies;

import org.json.JSONObject;

import dungeonmania.goals.Goal;

public class GoalBoulders implements Strategies {
    @Override
    public Goal setGoal(JSONObject jsonGoal, JSONObject config) {
        // TODO Auto-generated method stub
        return new Goal("boulders");
    }
}
