package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Random;

import org.json.JSONException;

public class AssassinTest {
    @Test
    @Tag("20-1")
    @DisplayName("Test assassin in line with Player moves towards them")
    public void simpleMovement() {
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        // P1       P2      P3      P4      A4      A3      A2      A1      .      Wall
        //                                  Wall    Wall   Wall    Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_simpleMovement", "c_assassinTest_simpleMovement");

        assertEquals(new Position(8, 1), getAssaPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getAssaPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getAssaPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getAssaPos(res));
    }

    @Test
    @Tag("20-2")
    @DisplayName("Test assassin stops if they cannot move any closer to the player")
    public void stopMovement() {
        //                  Wall     Wall    Wall
        // P1       P2      Wall      A1     Wall
        //                  Wall     Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_stopMovement", "c_assassinTest_stopMovement");

        Position startingPos = getAssaPos(res);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getAssaPos(res));
    }

    @Test
    @Tag("20-3")
    @DisplayName("Testing regardless of whether the bribe is successful or not, there will be no refund")
    public void bribeAmount() {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassinTest_bribeThreeDollar");

        String assaId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssaPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(assaId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssaPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(assaId));
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssaPos(res));

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(assaId));
        // regardless of whether the bribe is successful or not, there will be no refund
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("12-4")
    @DisplayName("Testing there is a chance that bribing assassins will fail.")
    public void mabyBribeFail() throws JSONException, IOException {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassinTest_bribeAmount");

        String assaId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssaPos(res));

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssaPos(res));

        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssaPos(res));

        // do bribe
        res = assertDoesNotThrow(() -> dmc.interact(assaId));
        // regardless of whether the bribe is successful or not, there will be no refund
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        Random randGen = new Random(30);
        double randomNum = randGen.nextDouble();

        // If the first bribe fails, take the second bribe.
        if (randomNum < 0.3) {
            res = assertDoesNotThrow(() -> dmc.interact(assaId));
            // regardless of whether the bribe is successful or not, there will be no refund
            assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        } else {
            // the bribe has already been successful, and the second bribe cannot be performed
            assertThrows(InvalidActionException.class, () -> dmc.interact(assaId));
            res = dmc.tick(Direction.RIGHT);
            assertEquals(0, res.getBattles().size());
        }
    }

    @Test
    @Tag("12-5")
    @DisplayName("Testing there is a chance that bribing assassins will fail.")
    public void mabyBribeFail2() throws JSONException, IOException {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassinTest_bribeAmount2");

        String assaId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssaPos(res));

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssaPos(res));

        // pick up third treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssaPos(res));

        // do bribe
        res = assertDoesNotThrow(() -> dmc.interact(assaId));
        // regardless of whether the bribe is successful or not, there will be no refund
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        Random randGen = new Random(90);
        double randomNum = randGen.nextDouble();

        // If the first bribe fails, take the second bribe.
        if (randomNum < 0.9) {
            res = assertDoesNotThrow(() -> dmc.interact(assaId));
            // regardless of whether the bribe is successful or not, there will be no refund
            assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        } else {
            // the bribe has already been successful, and the second bribe cannot be performed
            assertThrows(InvalidActionException.class, () -> dmc.interact(assaId));
            res = dmc.tick(Direction.RIGHT);
            assertEquals(0, res.getBattles().size());
        }
    }

    @Test
    @Tag("12-6")
    @DisplayName("Testing assassin battle with player")
    public void battle() throws JSONException, IOException {
        //                                                          Wall     Wall     Wall    Wall    Wall
        // P1       P2/Treasure      P3/Treasure    P4/Treasure      A4       A3       A2     A1      Wall
        //                                                          Wall     Wall     Wall    Wall    Wall
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassinTest_bribeAmount");

        // move to assassin
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, res.getBattles().size());
    }

    @Test
    @Tag("12-7")
    @DisplayName("Testing an allied assassin finds the player, then follow the player")
    public void allyMovementFollow() {
        /**
         * W W W - W W W W W E
         * P T W - - - - A W -
         * - W W - W W W W W -
         *
         * bribe_radius = 100
         * bribe_amount = 1
         */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_allyMovementFollow", "c_assassinTest_allyMovementFollow");

        String assaId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(6, 1), getAssaPos(res));

        // achieve bribe - success
        res = assertDoesNotThrow(() -> dmc.interact(assaId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssaPos(res));

        // Mercenary uses dijkstra to find the player
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 2), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 3), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 3), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(1, 3), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 3), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 2), getAssaPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 1), getAssaPos(res));

        // Ally follows the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(0, 1), getPlayerPos(res));
        assertEquals(new Position(1, 1), getAssaPos(res));

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 2), getPlayerPos(res));
        assertEquals(new Position(0, 1), getAssaPos(res));

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 3), getPlayerPos(res));
        assertEquals(new Position(0, 2), getAssaPos(res));
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }

    private Position getAssaPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }
}
