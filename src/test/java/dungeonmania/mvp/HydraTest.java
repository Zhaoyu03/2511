package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class HydraTest {
    @Test
    @Tag("21-1")
    @DisplayName("Testing hydra movement")
    public void movement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_movement", "c_hydraTest_movement");

        assertEquals(1, getHydra(res).size());

        // Teams may assume that random movement includes choosing to stay still, so we should just
        // check that they do move at least once in a few turns
        boolean hydraMoved = false;
        Position prevPosition = getHydra(res).get(0).getPosition();
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.UP);
            if (!prevPosition.equals(getHydra(res).get(0).getPosition())) {
                hydraMoved = true;
                break;
            }
        }
        assertTrue(hydraMoved);
    }

    @Test
    @Tag("20-2")
    @DisplayName("Testing hydra cannot move through closed doors and walls")
    public void doorsAndWalls() {
        //  W   W   W   W
        //  P   W   Z   W
        //      W   D   W
        //          K
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_doorsAndWalls", "c_hydraTest_movement");
        assertEquals(1, getHydra(res).size());
        Position position = getHydra(res).get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(position, getHydra(res).get(0).getPosition());
    }

    @Test
    @Tag("20-3")
    @DisplayName("Testing increase health fail in battle")
    public void battle1() {
        //         Wall     Wall     Wall    Wall    Wall
        // P1                                H1      Wall
        //         Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_battle", "c_hydraTest_noIncreaseHealth");

        // move to hudra
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        Random randGen = new Random(30);
        double randomNum = randGen.nextDouble();

        if (randomNum >= 0.3) {
            // hydra was killed on the first turn
            assertEquals(0, TestUtils.getEntities(res, "hydra").size());
            assertEquals(1, res.getBattles().size());
        }
    }

    @Test
    @Tag("20-4")
    @DisplayName("Testing Hydra wins by increase health")
    public void battle2() {
        //         Wall     Wall     Wall    Wall    Wall
        // P1                                H1      Wall
        //         Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_battle", "c_hydraTest_increaseHealth");

        assertEquals(1, TestUtils.getEntities(res, "hydra").size());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        Random randGen = new Random(90);
        double randomNum = randGen.nextDouble();

        if (randomNum < 0.9) {
            // hydra health increase 1200!!! hydra win!!!
            assertEquals(1, TestUtils.getEntities(res, "hydra").size());
        } else {
            assertEquals(0, TestUtils.getEntities(res, "hydra").size());
            assertEquals(1, res.getBattles().size());
        }
    }

    private List<EntityResponse> getHydra(DungeonResponse res) {
        return TestUtils.getEntities(res, "hydra");
    }
}
