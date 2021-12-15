package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameBoard;

import java.awt.event.KeyEvent;
import java.util.List;

import static model.CrossyRoadGame.GAME_WIDTH;
import static org.junit.jupiter.api.Assertions.*;

public class CrossyRoadGameTest {
    private CrossyRoadGame testGame;
    private PlayerProfile testPlayer;

    @BeforeEach
    void setUpTest() {
        testPlayer = new PlayerProfile("testPlayer");
        testGame = new CrossyRoadGame(testPlayer, true);
    }

    @Test
    void testConstructor() {
        assertEquals(testPlayer, testGame.getArcadePlayer());
        assertEquals(1, testGame.getCurrentLevel());
    }

    @Test
    void testRestartLevel() {
        testPlayer.increaseLevelAchieved();
        testGame = new CrossyRoadGame(testPlayer, false);
        assertEquals(2, testGame.getCurrentLevel());
        testGame = new CrossyRoadGame(testPlayer, true);
        assertEquals(1, testGame.getCurrentLevel());
    }



    @Test
    void testSetUpCrossyRoad() {
        assertEquals(testGame.getCars().size(), testGame.getNumRowsWithCars());

    }
    @Test
    void testConfigureDifficulty(){
        testGame.configureDifficulty();
        assertEquals(5, testGame.getNumRowsWithCars());
        assertEquals(700, testGame.getGameHeight());

        testGame.increaseLevel();
        testGame.configureDifficulty();
        assertEquals(6, testGame.getNumRowsWithCars());
        assertEquals(900, testGame.getGameHeight());
    }

    @Test
    void testMoveCars() {
        testGame.setCurrentLevel(1);
        testGame.setUpCrossyRoad();
        List<CrossyRoadCar> testCars = testGame.getCars();
        for (CrossyRoadCar testCar : testCars) {
            System.out.println(testCar.getCarPositionX());
            testCar.setDelayTime(0);
        }
        testGame.moveCars();
        for (CrossyRoadCar testCar : testGame.getCars()) {
            System.out.println(testCar.getCarIdentifier());
            if (testCar.getMovementDirection().equals("right")) {
                assertEquals(testCar.getCarLength() * -1 * GameBoard.PIXELS_PER_UNIT + testCar.getVelocity(),
                        testCar.getCarPositionX());
            } else {
                assertEquals(CrossyRoadGame.GAME_WIDTH + testCar.getCarLength()  * GameBoard.PIXELS_PER_UNIT - testCar.getVelocity(),
                        testCar.getCarPositionX());
            }
        }


    }

//    @Test
//    void testisCarOutOfBoundary() {
//        CrossyRoadCar testCar = new CrossyRoadCar(-300, 0, 2, 3,
//                3, 0, "left");
//        assertFalse(testGame.isCarOutOfBoundary(testCar));
//        testCar = new CrossyRoadCar(-300, 0, 2, 2,
//                3, 0, "left");
//        assertTrue(testGame.isCarOutOfBoundary(testCar));
//    }

    @Test
    void testCheckCollision() {
        CrossyRoadCar testCar = new CrossyRoadCar(0, 0, 1, 3,
                3, 0, "right");
        for (int i = 0; i < CrossyRoadGame.GAME_WIDTH / 2; i++) {
            testCar.moveCar();
        }
        assertTrue(testGame.checkCollision(testCar));
        for (int i = 0; i < 250; i++) {
            testCar.moveCar();
        }
        assertFalse(testGame.checkCollision(testCar));
        testGame.getCrossyRoadPlayer().movePlayer(KeyEvent.VK_A);
        assertFalse(testGame.checkCollision(testCar));
    }




    @Test
    void testGenerateCoordinateYList() {
        testGame.generateCarCoordinates();
        assertEquals(testGame.getCarCoordinates().size(), testGame.getGameHeight() / GameBoard.PIXELS_PER_UNIT);
        for (int positionY : testGame.getCarCoordinates()) {
            assertTrue(positionY >= 0 && positionY < testGame.getGameHeight());
        }

    }

    @Test
    void testGenerateCars() {
        testGame.clearCars();
        testGame.generateCars(testGame.getNumRowsWithCars());
        List<CrossyRoadCar> cars = testGame.getCars();
        assertEquals(5, cars.size());
    }

    @Test
    void testReplaceCar() {
        CrossyRoadCar testCar = new CrossyRoadCar(0, 0, 1 ,1 , 1, 0, "right");
        CrossyRoadCar testCar2 = new CrossyRoadCar(GAME_WIDTH, 0, 1 ,1 , 2, 0,  "left");
        CrossyRoadCar replacementCar = testGame.replaceCar(testCar);
        assertEquals(1, replacementCar.getCarIdentifier());
        replacementCar = testGame.replaceCar(testCar2);
        assertEquals(2, replacementCar.getCarIdentifier());
        assertEquals(GAME_WIDTH + replacementCar.getCarLength()  * GameBoard.PIXELS_PER_UNIT, replacementCar.getCarPositionX());

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
    void testGetCrossyRoadPlayer() {
        CrossyRoadPlayer player = testGame.getCrossyRoadPlayer();
        assertEquals(testGame.getCrossyRoadPlayer(), player);
    }

    @Test
    void setLevelTest() {
        testGame.setCurrentLevel(2);
        assertEquals(2, testGame.getCurrentLevel());
    }

}
