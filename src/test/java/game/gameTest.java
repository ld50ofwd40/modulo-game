package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class gameTest {

    private Game game;

    int[][] testTable = new int[5][5];

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @BeforeEach
    void createTestTable(){
        for(int i=0; i<=4; i++){
            for(int j=0; j<=4; j++){
                testTable[i][j] = game.getElement(i, j);
            }
        }
    }

    @Test
    void testUpdateTableValues(){
        testTable[1][1]=2;
        testTable[1][2]=2;
        testTable[1][3]=2;
        testTable[2][1]=2;
        testTable[2][3]=2;
        testTable[3][1]=2;
        testTable[3][2]=2;
        testTable[3][3]=2;
        game.updateTableValues(2,2,1,4);
        for(int i=0; i<=4; i++) {
            for (int j = 0; j <= 4; j++) {
                assertEquals(0, testTable[i][j]-game.getElement(i, j));
            }
        }
    }

    @Test
    void testGetX(){
        assertEquals(0, game.getPlayerX());
    }

    @Test
    void testGetY(){
        assertEquals(0, game.getPlayerY());
    }

    @Test
    void testIsSolved(){
        assertEquals(false,game.isSolved());
    }
}
