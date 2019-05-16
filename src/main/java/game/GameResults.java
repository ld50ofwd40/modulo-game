package game;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a single player.
 */
class Player{
    private String name;
    private int steps;
    private int modulo;

    Player(String name, int steps, int modulo){
        this.name = name;
        this.steps = steps;
        this.modulo = modulo;
    }

    public String getName() {
        return name;
    }

    public int getSteps() {
        return steps;
    }

    public int getModulo() {
        return modulo;
    }
}

/**
 * Class representing results of the players.
 */
@Slf4j
public class GameResults {

    /**
     * The list of the players.
     */
    private List<Player> storedPlayers;

    private FileOperations fileOperations;

    GameResults(){
        storedPlayers = new ArrayList<Player>();
        fileOperations = new FileOperations();
        loadPlayers();
    }

    /**
     * Creates a new {@link Player} and adds it to the list.
     *
     * @param name the name of the player
     * @param steps the steps of the player
     * @param modulo the module of the game
     */
    public void addPlayer(String name, int steps, int modulo){
        storedPlayers.add(new Player(name, steps, modulo));
    }

    /**
     * Updates the results with new records.
     */
    public void update() {
        try {

            JsonWriter jsonWriter = fileOperations.CreateJsonWriter("results.json");

            jsonWriter.beginObject();
            jsonWriter.name("players");
            jsonWriter.beginArray();
            for (Player player : storedPlayers) {
                jsonWriter.beginArray();
                jsonWriter.value(player.getName());
                jsonWriter.value(player.getSteps());
                jsonWriter.value(player.getModulo());
                jsonWriter.endArray();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();

            jsonWriter.close();
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    /**
     * Loads players data from file.
     */
    private void loadPlayers(){
        String name = "";
        int steps = 0;
        int modulo = 0;
        try {

            JsonReader jsonReader = fileOperations.CopyFileFromJar("results.json");

            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();

            while (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                if (jsonReader.peek() == JsonToken.END_ARRAY) {
                    jsonReader.endArray();
                } else if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
                    jsonReader.beginArray();
                } else if (jsonReader.peek() == JsonToken.STRING) {
                    name = jsonReader.nextString();
                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    steps = jsonReader.nextInt();
                    if (jsonReader.peek() == JsonToken.NUMBER) {
                        modulo = jsonReader.nextInt();
                        storedPlayers.add(new Player(name, steps, modulo));
                    }
                } else if (jsonReader.peek() == JsonToken.END_OBJECT) {
                    jsonReader.endObject();
                }
            }

            jsonReader.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
