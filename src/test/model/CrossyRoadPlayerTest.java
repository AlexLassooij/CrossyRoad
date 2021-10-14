package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CrossyRoadPlayerTest {
    private CrossyRoadPlayer testCrossyRoadPlayer;

    @BeforeEach
    void setUpTest() {
        this.testCrossyRoadPlayer = new CrossyRoadPlayer("testPlayer");
    }

    @Test
    void testConstructor() {
        assertEquals(CrossyRoadGame.GAME_WIDTH / 2, testCrossyRoadPlayer.getPositionX());
        assertEquals(0, testCrossyRoadPlayer.getPositionY());
        assertEquals("testPlayer", testCrossyRoadPlayer.getCrossyRoadPlayerName());
    }

    @Test
    void testMovePlayer() {
        testCrossyRoadPlayer.movePlayer("w");
        assertEquals(1, testCrossyRoadPlayer.getPositionY());
        testCrossyRoadPlayer.movePlayer("w");
        assertEquals(2, testCrossyRoadPlayer.getPositionY());
        testCrossyRoadPlayer.movePlayer("a");
        assertEquals(CrossyRoadGame.GAME_WIDTH / 2 - 1, testCrossyRoadPlayer.getPositionX());
    }

    @Test
    void testCheckBoundariesAndMove() {
        for (int i = 0; i < CrossyRoadGame.GAME_WIDTH / 2; i++) {
            testCrossyRoadPlayer.movePlayer("a");
            assertEquals(CrossyRoadGame.GAME_WIDTH / 2 - (i + 1), testCrossyRoadPlayer.getPositionX());
        }
        testCrossyRoadPlayer.movePlayer("a");
        assertEquals(0, testCrossyRoadPlayer.getPositionX());
        testCrossyRoadPlayer.checkBoundariesAndMove("s", 0);
        assertEquals(0, testCrossyRoadPlayer.getPositionX());


        assertEquals(0, testCrossyRoadPlayer.checkBoundariesAndMove(
                "a", 0));
        assertEquals(0, testCrossyRoadPlayer.checkBoundariesAndMove(
                "s", 0));
        assertEquals(6, testCrossyRoadPlayer.checkBoundariesAndMove(
                "w", 5));
        assertEquals(CrossyRoadGame.GAME_WIDTH - 1, testCrossyRoadPlayer.checkBoundariesAndMove(
                "r", CrossyRoadGame.GAME_WIDTH - 1));
    }
}
