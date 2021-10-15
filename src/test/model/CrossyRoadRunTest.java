package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrossyRoadRunTest {
    private CrossyRoadRun testCrossyRoadRun;
    private PlayerProfile player;

    @BeforeEach
    void setUpTest() {
        player = new PlayerProfile("testPlayer");
        testCrossyRoadRun = new CrossyRoadRun(player);

    }

    @Test
    void testConstructor() {
        assertTrue(testCrossyRoadRun.getTimerStatus());
    }

    @Test
    void testStopTimer() {
        testCrossyRoadRun.stopTimer();
        assertFalse(testCrossyRoadRun.getTimerStatus());
    }
}
