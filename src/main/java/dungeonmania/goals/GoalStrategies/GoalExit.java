package dungeonmania.goals.GoalStrategies;

// import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goals.Goal;

public class GoalExit implements Strategies {
    @Override
    public Goal setGoal(JSONObject jsonGoal, JSONObject config) {
        // TODO Auto-generated method stub
        //JSONArray subgoals;
        return new Goal("exit");
    }
}
