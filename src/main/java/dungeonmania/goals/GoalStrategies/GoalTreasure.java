package dungeonmania.goals.GoalStrategies;

import org.json.JSONObject;

import dungeonmania.goals.Goal;

public class GoalTreasure implements Strategies {
    @Override
    public Goal setGoal(JSONObject jsonGoal, JSONObject config) {
        // TODO Auto-generated method stub
        int treasureGoal = config.optInt("treasure_goal", 1);
        return new Goal("treasure", treasureGoal);
    }
}
