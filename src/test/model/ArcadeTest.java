package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcadeTest {
    private Arcade testArcade;
    // delete or rename this class!
    @BeforeEach
    void setUpTest() {
        testArcade = new Arcade(true);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testArcade.getPlayerProfileList().size());
    }

    @Test
    void testCreateNewProfile() {
        testArcade.createNewProfile();
        assertEquals(1, testArcade.getPlayerProfileList().size());
        assertEquals("testPlayer", testArcade.getPlayerProfileList().get(0).getPlayerName());
    }
}