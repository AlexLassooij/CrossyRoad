package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameBoard;


import static model.CrossyRoadGame.GAME_WIDTH;
import static org.junit.jupiter.api.Assertions.*;


public class CrossyRoadCarTest {
    private CrossyRoadCar testCar;
    private CrossyRoadCar testCar2;


    @BeforeEach
    void setUpTest() {
        testCar = new CrossyRoadCar(0, 0, 1 ,1 , 1, 0, "right");
        testCar2 = new CrossyRoadCar(GAME_WIDTH, 0, 1 ,1 , 2, 0,  "left");
    }

    @Test
    void testConstructor() {
        assertNotNull(testCar.getCarColour());

    }

    @Test
    void testMoveCar() {
        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar.moveCar();
        }
        assertEquals(GameBoard.PIXELS_PER_UNIT, testCar.getCarPositionX());
        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar.moveCar();
        }
        assertEquals(2 * GameBoard.PIXELS_PER_UNIT, testCar.getCarPositionX());
        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar.moveCar();
        }
        assertEquals(3 * GameBoard.PIXELS_PER_UNIT, testCar.getCarPositionX());

        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar2.moveCar();
        }
        assertEquals(GAME_WIDTH - GameBoard.PIXELS_PER_UNIT, testCar2.getCarPositionX());
        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar2.moveCar();
        }
        assertEquals(GAME_WIDTH - 2 * GameBoard.PIXELS_PER_UNIT, testCar2.getCarPositionX());
        for (int i = 0 ; i < GameBoard.PIXELS_PER_UNIT; i++) {
            testCar2.moveCar();
        }
        assertEquals(GAME_WIDTH - 3 * GameBoard.PIXELS_PER_UNIT, testCar2.getCarPositionX());
    }

    @Test
    void testGetCarInformation() {
        assertEquals(0, testCar.getCarPositionX());
        assertEquals(0, testCar.getCarPositionY());
        assertEquals(1, testCar.getCarLength());

        for (int i = 0; i < 4; i++) {
            testCar.moveCar();
        }
        assertEquals(4, testCar.getCarPositionX());
        assertEquals(0, testCar.getCarPositionY());
        assertEquals(1, testCar.getCarLength());

    }
}
