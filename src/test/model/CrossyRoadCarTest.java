package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;


public class CrossyRoadCarTest {
    private CrossyRoadCar testCar;
    private CrossyRoadCar testCar2;


    @BeforeEach
    void setUpTest() {
        testCar = new CrossyRoadCar(0, 0, 1 ,1 , 1, "right");
        testCar2 = new CrossyRoadCar(4, 0, 1 ,1 , 2, "left");
    }

    @Test
    void testMoveCar() {
        testCar.moveCar();
        assertEquals(1, testCar.getCarPositionX());
        testCar.moveCar();
        assertEquals(2, testCar.getCarPositionX());
        testCar.moveCar();
        assertEquals(3, testCar.getCarPositionX());

        testCar2.moveCar();
        assertEquals(3, testCar2.getCarPositionX());
        testCar2.moveCar();
        assertEquals(2, testCar2.getCarPositionX());
        testCar2.moveCar();
        assertEquals(1, testCar2.getCarPositionX());
    }

    @Test
    void testGetCarInformation() {
        Hashtable<String, Integer> infoTable = testCar.getCarInformation();
        assertEquals(0, infoTable.get("positionX"));
        assertEquals(0, infoTable.get("positionY"));
        assertEquals(1, infoTable.get("carLength"));

        infoTable = testCar2.getCarInformation();
        assertEquals(4, infoTable.get("positionX"));
        assertEquals(0, infoTable.get("positionY"));
        assertEquals(1, infoTable.get("carLength"));

    }
}
