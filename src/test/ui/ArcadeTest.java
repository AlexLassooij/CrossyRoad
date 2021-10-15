//package ui;
//
//import jdk.nashorn.internal.ir.annotations.Ignore;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ArcadeTest {
//    private Arcade testArcade;
//
//    @Ignore
//    @BeforeEach
//    void setUpTest() {
//        testArcade = new Arcade();
//        testArcade.createNewProfile();
//    }
//
//    @Ignore
//    @Test
//    void testConstructor() {
//        assertEquals(0, testArcade.getPlayerProfileList().size());
//    }
//
//    @Ignore
//    @Test
//    void testCreateNewProfile() {
//        testArcade.createNewProfile();
//        assertEquals(2, testArcade.getPlayerProfileList().size());
//        assertEquals("testPlayer", testArcade.getPlayerProfileList().get(1).getPlayerName());
//    }
//}