package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerProfileTest {
    private PlayerProfile testPlayerProfile;

    @BeforeEach
    void setUpTest() {
        testPlayerProfile = new PlayerProfile("testPlayer");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testPlayerProfile.getPoints());
        assertEquals(1, testPlayerProfile.getLevelAchieved());
        assertEquals("testPlayer", testPlayerProfile.getPlayerName());
    }

    @Test
    void testChangePlayerName() {
        testPlayerProfile.changePlayerName("Player 1");
        assertEquals("Player 1", testPlayerProfile.getPlayerName());
    }

    @Test
    void testAddPoints() {
        testPlayerProfile.addPoints(20);
        assertEquals(20, testPlayerProfile.getPoints());
    }

    @Test
    void testIncreaseLevelAchieved() {
        testPlayerProfile.increaseLevelAchieved();
        assertEquals(2, testPlayerProfile.getLevelAchieved());
        testPlayerProfile.increaseLevelAchieved();
        assertEquals(3, testPlayerProfile.getLevelAchieved());
    }
}
