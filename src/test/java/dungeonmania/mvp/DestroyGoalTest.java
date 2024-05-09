package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DestroyGoalTest {
    @Test
    @Tag("18-1")
    @DisplayName("Test achieving a basic destroy goal")
    public void destroy() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_basicGoalsTest_destroy", "c_basicGoalsTest_tenDestroy");

        //  assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":destroy"));

        List<EntityResponse> entities = res.getEntities();
        int swordNum = TestUtils.countEntityOfType(entities, "sword");
        int spiderNum = TestUtils.countEntityOfType(entities, "spider");
        int mercenaryNum = TestUtils.countEntityOfType(entities, "mercenary");
        int playerNum = TestUtils.countEntityOfType(entities, "player");
        // check number of entities
        assertEquals(1, swordNum);
        assertEquals(1, spiderNum);
        assertEquals(10, mercenaryNum);
        assertEquals(1, playerNum);

        //  move player to sword
        res = dmc.tick(Direction.RIGHT);

        //  assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":destroy"));

        //  move player to five enemies
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        entities = res.getEntities();
        swordNum = TestUtils.countEntityOfType(entities, "sword");
        spiderNum = TestUtils.countEntityOfType(entities, "spider");
        mercenaryNum = TestUtils.countEntityOfType(entities, "mercenary");
        playerNum = TestUtils.countEntityOfType(entities, "player");
        // check number of entities
        assertEquals(0, swordNum);
        assertEquals(1, spiderNum);
        assertEquals(5, mercenaryNum);
        assertEquals(1, playerNum);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":destroy"));

        // move player to five enemies
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);

        entities = res.getEntities();
        swordNum = TestUtils.countEntityOfType(entities, "sword");
        spiderNum = TestUtils.countEntityOfType(entities, "spider");
        mercenaryNum = TestUtils.countEntityOfType(entities, "mercenary");
        playerNum = TestUtils.countEntityOfType(entities, "player");
        // check number of entities
        assertEquals(0, swordNum);
        assertEquals(1, spiderNum);
        assertEquals(1, playerNum);
        assertEquals(0, mercenaryNum);
        assertEquals(1, playerNum);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }
}
