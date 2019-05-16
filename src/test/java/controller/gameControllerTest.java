package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class gameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    void testInitialize(){
        gameController.initdata(4,"Name");
        assertNotNull(gameController.userName);
    }



}
