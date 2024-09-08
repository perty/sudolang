import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        // Assuming there is a method to initialize the caves and place hazards
        game.initCaves();
        game.placeHazards();
    }

    @Test
    public void testInitialPlayerPlacement() {
        assertEquals(1, game.getPlayerCave(), "Player should start in cave 1");
    }

    @Test
    public void testMoveToConnectedCave() {
        game.movePlayer(2); // Assuming cave 1 is connected to cave 2
        assertEquals(2, game.getPlayerCave(), "Player should move to cave 2");
    }

    @Test
    public void testMoveToNonConnectedCave() {
        game.movePlayer(3); // Assuming cave 1 is not connected to cave 3
        assertNotEquals(3, game.getPlayerCave(), "Player should not move to a non-connected cave");
    }

    @Test
    public void testArrowHitsMinotaur() {
        game.setMinotaurCave(3); // Manually place Minotaur for test
        assertTrue(game.shootArrow(new int[]{2, 3}), "Arrow should hit the Minotaur");
    }

    @Test
    public void testArrowMissesMinotaur() {
        game.setMinotaurCave(4);
        assertFalse(game.shootArrow(new int[]{2, 3}), "Arrow should miss the Minotaur");
    }

    @Test
    public void testFallIntoPit() {
        game.setPitCaves(new int[]{2, 5}); // Manually place pits for test
        game.movePlayer(2);
        assertTrue(game.isGameOver(), "Player should fall into pit");
    }

    @Test
    public void testMoveCommand() {
        game.processCommand("move to 2");
        assertEquals(2, game.getPlayerCave(), "Player should move to cave 2.");
    }

    @Test
    public void testShootCommandHitsMinotaur() {
        game.setMinotaurCave(3);
        game.processCommand("shoot 2 3");
        assertTrue(game.isGameOver(), "Shooting arrow should hit the Minotaur and end the game.");
    }

    @Test
    public void testInvalidCommand() {
        int initialCave = game.getPlayerCave();
        game.processCommand("jump 2");
        assertEquals(initialCave, game.getPlayerCave(), "Invalid command should not change player position.");
    }
}
