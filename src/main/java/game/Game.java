package game;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.abs;
import static java.lang.Math.floorMod;

/**
 * Class representing and managing the game.
 */
@Slf4j
public class Game {

    /**
     * The table representing the fields of the game.
     */
    private int[][] table;

    /**
     * The position of the player.
     */
    private int[] playerPos;
    private GameResults gameResults;

    private FileOperations fileOperations;

    public Game(){
        gameResults = new GameResults();
        playerPos = new int[2];
        table = new int[5][5];
        fileOperations = new FileOperations();
        uploadTable();
    }

    /**
     * Loads data representing the state of the game from file.
     */
    private void uploadTable(){
        try {

            JsonReader jsonReader = fileOperations.CopyFileFromJar("table.json");

            jsonReader.beginObject();
            int row = 0;
            int col = 0;
            while (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                if (jsonReader.peek() == JsonToken.END_ARRAY) {
                    jsonReader.endArray();
                    if (row < table[0].length && col < table[1].length) {
                        throw new IllegalArgumentException("Not enough elements in the " + row + ". row");
                    }
                    row++;
                    col = 0;

                } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();


                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    table[row][col] = jsonReader.nextInt();
                    col++;

                } else if (jsonReader.peek() == JsonToken.NAME) {
                    jsonReader.nextName();

                } else if (jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();

                }
            }
            jsonReader.close();
        } catch (Exception e){
            log.error(e.toString());
            uploadDefaultTable();
        }
    }

    /**
     * Loads the default table if the source file doesn't available.
     */
    private void uploadDefaultTable() {
        int[][] table = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        this.table = table;
    }

    public int getPlayerX(){
        return playerPos[0];
    }

    public void setPlayerX(int x){
        playerPos[0] = x;
    }

    public int getPlayerY(){
        return playerPos[1];
    }

    public void setPlayerY(int y){
        playerPos[1] = y;
    }

    public int getElement(int i, int j){
        return table[i][j];
    }

    /**
     * Updates the neighboring fields of the player's current field.
     * @param x the player's first coorditane
     * @param y the player's second coordinate
     * @param fieldValue the value of the player's current field
     * @param modulo the module of the game
     */
    public void updateTableValues(int x, int y, int fieldValue, int modulo){
        for(int i=x-1; i<=x+1; i++){
            for(int j=y-1; j<=y+1; j++){
                if(abs(i - x) + abs(j - y) != 0){
                    table[floorMod(i,5)][floorMod(j,5)] = floorMod(table[floorMod(i,5)][floorMod(j,5)] + fieldValue, modulo);
                }
            }
        }
        log.info("Player's new position: {},{}",x,y);
    }

    /**
     * Updates results with the current player's data if the player wins.
     * @param name the name of the player
     * @param steps the steps the player made until the end of the game
     * @param modulo the module of the game
     */
    public void updateResults(String name, int steps, int modulo){
        gameResults.addPlayer(name, steps, modulo);
        gameResults.update();
        log.info("result.json has updated");
    }

    /**
     * Returns whether the player reached a field with the value of 0.
     * @return {@code true} if the value of the player's current field is 0, {@code true} otherwise
     */
    public boolean isSolved(){
        if(table[playerPos[0]][playerPos[1]]==0){
            return true;
        }
        return false;
    }
}
