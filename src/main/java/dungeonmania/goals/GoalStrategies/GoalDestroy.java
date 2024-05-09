package dungeonmania.goals.GoalStrategies;

import org.json.JSONObject;

import dungeonmania.goals.Goal;

public class GoalDestroy implements Strategies {
    public Goal setGoal(JSONObject jsonGoal, JSONObject config) {
        // TODO Auto-generated method stub
        int destroyGoal = config.optInt("destroy_goal", 10);
        return new Goal("destroy", destroyGoal);
    }

}
