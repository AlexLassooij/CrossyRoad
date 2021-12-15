package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String JSON_STORE = "./data/testWriter.json";

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\0invalid.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterCommonCase() {
        try {
            Arcade arcade = new Arcade();
            arcade.setJsonWriter( new JsonWriter(JSON_STORE));
            PlayerProfile player1 = new PlayerProfile("player1");
            PlayerProfile player2 = new PlayerProfile("player2");
            PlayerProfile player3 = new PlayerProfile("player3");
            arcade.addPlayerProfile(player1, "CROSSYROAD");
            arcade.addPlayerProfile(player2, "CROSSYROAD");
            arcade.addPlayerProfile(player3, "MEMORY");
            arcade.saveArcade();
            arcade.saveArcade();
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            jsonReader.read(arcade);

            List<PlayerProfile> crossyroadPlayers = arcade.getPlayerProfileList("CROSSYROAD");
            List<PlayerProfile> memoryPlayers = arcade.getPlayerProfileList("MEMORY");
            assertEquals(2, crossyroadPlayers.size());
            assertEquals(1, memoryPlayers.size());
            assertEquals("player1", crossyroadPlayers.get(0).getPlayerName());
            assertEquals("player2", crossyroadPlayers.get(1).getPlayerName());
            assertEquals("player3", memoryPlayers.get(0).getPlayerName());

        } catch (IOException e) {
            // pass
        }
    }
}
