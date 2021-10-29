package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private static final String JSON_STORE = "./data/testReader.json";
    private JsonReader reader;

    @BeforeEach
    public void setUpTest() {
        this.reader = new JsonReader(JSON_STORE);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Arcade arcade = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoPlayers() {
        JsonReader emptyReader = new JsonReader("./data/testReaderEmpty.json");
        try {
            Arcade arcade = emptyReader.read();
            assertEquals(0, arcade.getPlayerProfileList("CROSSYROAD").size());
            assertEquals(0, arcade.getPlayerProfileList("MEMORY").size());
            assertEquals(0, arcade.getPlayerProfileList("TEST").size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWithPlayers() {
        try {
            Arcade arcade = reader.read();
            List<PlayerProfile> crossyroadPlayers = arcade.getPlayerProfileList("CROSSYROAD");
            List<PlayerProfile> memoryPlayers = arcade.getPlayerProfileList("MEMORY");
            assertEquals(2, crossyroadPlayers.size());
            assertEquals(1, memoryPlayers.size());
            assertEquals("player1", crossyroadPlayers.get(0).getPlayerName());
            assertEquals("player2", crossyroadPlayers.get(1).getPlayerName());
            assertEquals("player3", memoryPlayers.get(0).getPlayerName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

 }
