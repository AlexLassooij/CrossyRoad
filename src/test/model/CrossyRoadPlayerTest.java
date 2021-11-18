package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameBoard;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;


public class CrossyRoadPlayerTest {
    private CrossyRoadPlayer testCrossyRoadPlayer;

    @BeforeEach
    void setUpTest() {
        this.testCrossyRoadPlayer = new CrossyRoadPlayer("testPlayer");
    }

    @Test
    void testConstructor() {
        assertEquals(((CrossyRoadGame.GAME_WIDTH / 100) / 2) * 100, testCrossyRoadPlayer.getPositionX());
        assertEquals(0, testCrossyRoadPlayer.getPositionY());
        assertEquals("testPlayer", testCrossyRoadPlayer.getCrossyRoadPlayerName());
        assertNotNull(testCrossyRoadPlayer.getPlayerColor());
    }

    @Test
    void testMovePlayer() {
        testCrossyRoadPlayer.movePlayer(KeyEvent.VK_W);
        assertEquals(GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.getPositionY());
        testCrossyRoadPlayer.movePlayer(KeyEvent.VK_W);
        assertEquals(2 * GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.getPositionY());
        testCrossyRoadPlayer.movePlayer(KeyEvent.VK_A);
        assertEquals(((CrossyRoadGame.GAME_WIDTH / 100) / 2) * 100 - GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.getPositionX());
    }
    @Test
    void testCheckBoundariesAndMove() {
        for (int i = 0; i < ((CrossyRoadGame.GAME_WIDTH / 100) / 2); i++) {
            testCrossyRoadPlayer.movePlayer(KeyEvent.VK_A);
            assertEquals(((CrossyRoadGame.GAME_WIDTH / 100) / 2) * 100 - (i + 1) * GameBoard.PIXELS_PER_UNIT,
                    testCrossyRoadPlayer.getPositionX());
        }
        testCrossyRoadPlayer.movePlayer(KeyEvent.VK_A);
        assertEquals(0, testCrossyRoadPlayer.getPositionX());
        testCrossyRoadPlayer.checkBoundariesAndMove(KeyEvent.VK_S, 0);
        assertEquals(0, testCrossyRoadPlayer.getPositionX());
        assertEquals(0, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_A, 0));
        assertEquals(0, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_S, 0));
        assertEquals(500 + GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_W, 500));
        assertEquals(CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_D, CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT));
        assertEquals(CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_D, CrossyRoadGame.GAME_WIDTH - 2 * GameBoard.PIXELS_PER_UNIT));
        assertEquals(10, testCrossyRoadPlayer.checkBoundariesAndMove(
                KeyEvent.VK_SPACE, 10));
    }
}
