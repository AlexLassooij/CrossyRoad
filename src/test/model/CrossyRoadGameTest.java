package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameBoardGenerator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CrossyRoadGameTest {
    private CrossyRoadGame testGame;
    private PlayerProfile testPlayer;

    @BeforeEach
    void setUpTest() {
        testPlayer = new PlayerProfile("testPlayer");
        testGame = new CrossyRoadGame(testPlayer);
    }

    @Test
    void testConstructor() {
        assertEquals(testPlayer, testGame.getArcadePlayer());
        assertEquals(1, testGame.getCurrentLevel());
    }

    @Test
    void testSetUpCrossyRoad() {
        assertEquals(testGame.getCars().size(), testGame.getNumCars());

    }
    @Test
    void testConfigureDifficulty(){
        testGame.configureDifficulty();
        assertEquals("START", testGame.getGameStatus());
        assertEquals(1000, testGame.getMovementInterval());
        assertEquals(3, testGame.getNumCars());
        assertEquals(5, testGame.getGameHeight());

        testGame.increaseLevel();
        testGame.configureDifficulty();
        assertEquals("START", testGame.getGameStatus());
        assertEquals(950, testGame.getMovementInterval());
        assertEquals(4, testGame.getNumCars());
        assertEquals(7, testGame.getGameHeight());
    }

    @Test
    void testMoveCars() {
        List<CrossyRoadCar> testCars = testGame.getCars();
        List<Integer> startingPositons = new ArrayList<>();
        for (CrossyRoadCar testCar : testCars) {
            startingPositons.add(testCar.getCarPositionX());
        }
        testGame.moveCars();
        for (int i = 0; i < testGame.getNumCars(); i++) {
            int startingPosition = startingPositons.get(i);
            CrossyRoadCar testCar = testCars.get(i);
            if (startingPosition == 0) {
                assertEquals(1, testCar.getCarPositionX());
            } else {
                assertEquals(CrossyRoadGame.GAME_WIDTH - 2, testCar.getCarPositionX());
            }
        }

    }

    @Test
    void testisCarOutOfBoundary() {
        CrossyRoadCar testCar = new CrossyRoadCar(-2, 0, 2, 3,
                3, "left");
        assertFalse(testGame.isCarOutOfBoundary(testCar));
        testCar = new CrossyRoadCar(-2, 0, 2, 2,
                3, "left");
        assertTrue(testGame.isCarOutOfBoundary(testCar));
    }

    @Test
    void testCheckCollision() {
        CrossyRoadCar testCar = new CrossyRoadCar(0, 0, 2, 3,
                3, "right");
        for (int i = 0; i < CrossyRoadGame.GAME_WIDTH / 2; i++) {
            testCar.moveCar();
        }
        assertTrue(testGame.checkCollision(testCar));
        testCar.moveCar();
        assertTrue(testGame.checkCollision(testCar));
        testCar.moveCar();
        assertTrue(testGame.checkCollision(testCar));
        testCar.moveCar();
        assertFalse(testGame.checkCollision(testCar));
    }

    @Test
    void testGenerateCoordinateYList() {
        List<Integer> coordinateListY = testGame.generateCoordinateListY();
        assertEquals(coordinateListY.size(), testGame.getGameHeight());
        for (int positionY : coordinateListY) {
            assertTrue(positionY >= 0 && positionY < testGame.getGameHeight());
        }

    }

    @Test
    void testGenerateCarLengthList() {
        List<Integer> carLengthList = testGame.generateCarLengthList();
        assertEquals(carLengthList.size(), CrossyRoadGame.MAX_CAR_LENGTH);
        for (int carLength : carLengthList) {
            assertTrue(carLength >= 1 && carLength <= CrossyRoadGame.MAX_CAR_LENGTH);
        }
    }

    @Test
    void testGenerateCarInfo() {
        testGame.increaseLevel();
        List<Integer> coordinateListY = testGame.generateCoordinateListY();
        List<Integer> carLengthList = testGame.generateCarLengthList();
        Hashtable<String, Integer> infoTable = testGame.generateCarInfo(coordinateListY, carLengthList);
        int positionX = infoTable.get("positionX");
        int positionY = infoTable.get("positionY");
        int carLength = infoTable.get("carLength");
        int velocity = infoTable.get("velocity");
        assertTrue(positionX >= 0 && positionY < CrossyRoadGame.GAME_WIDTH);
        assertTrue(positionY >= 0 && positionY < testGame.getGameHeight());
        assertTrue(carLength >= 1 && carLength <= CrossyRoadGame.MAX_CAR_LENGTH);
        assertTrue(velocity >= 1 && velocity <= 2);



    }

    @Test
    void testGenerateCars() {
        testGame.clearCars();
        testGame.generateCars(testGame.getGameHeight());
        List<Integer> occupiedYList = new ArrayList<>();
        for (CrossyRoadCar car : testGame.getCars()) {
            int positionY = car.getCarPositionY();
            assertFalse(occupiedYList.contains(positionY));
            occupiedYList.add(positionY);
        }
    }

    @Test
    void testReplaceCar() {
        CrossyRoadCar testCar = testGame.getCars().get(0);
        int carIdentifier = testCar.getCarIdentifier();
        int positionY = testCar.getCarPositionY();
        CrossyRoadCar replacementCar = testGame.replaceCar(carIdentifier, positionY);
        assertEquals(carIdentifier, replacementCar.getCarIdentifier());

        testGame.setNumCars(testGame.getGameHeight());
        replacementCar = testGame.replaceCar(carIdentifier, positionY);
        assertEquals(carIdentifier, replacementCar.getCarIdentifier());
        assertEquals(positionY, replacementCar.getCarPositionY());
    }

    @Test
    void testCheckCompletion() {
        assertFalse(testGame.checkCompletion());
    }

    @Test
    void testSetGameStatus() {
        testGame.setGameStatus("TEST");
        assertEquals("TEST", testGame.getGameStatus());
    }

    @Test
    void testGetGameBoard() {
        GameBoardGenerator gameBoard = testGame.getGameBoard();
        assertEquals(testGame.getGameBoard(), gameBoard);
    }

    @Test
    void testGetCrossyRoadPlayer() {
        CrossyRoadPlayer player = testGame.getCrossyRoadPlayer();
        assertEquals(testGame.getCrossyRoadPlayer(), player);
    }

}
