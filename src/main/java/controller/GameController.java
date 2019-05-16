package controller;

import game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import static java.lang.Math.floorMod;

public class GameController {

    @FXML
    private Pane thePane;
    @FXML
    private List<Label> textList ;
    @FXML
    private List<Rectangle> rectList;
    @FXML
    private Label numberOfSteps;

    public int modulo;
    public String userName;
    private Game game;
    private int steps;

    public void initialize(){
        game = new Game();
        game.setPlayerX(2);
        game.setPlayerY(2);

        for(int i=0; i<=4; i++){
            for(int j=0; j<=4; j++){
                textList.get(i*5+j).setText(String.valueOf(game.getElement(i,j)));
                textList.get(i*5+j).setTextFill(Color.WHITE);
            }
        }
        textList.get(game.getPlayerX()*5+game.getPlayerX()).setTextFill(Color.RED);
        for(int i=0; i<=4; i++){
            for(int j=0; j<=4; j++){
                rectList.get(i*5+j).setTranslateX(j*54);
                rectList.get(i*5+j).setTranslateY(i*54);
                rectList.get(i*5+j).setFill(Color.CORNFLOWERBLUE);
                rectList.get(i*5+j).setStroke(Color.BLACK);
                rectList.get(i*5+j).setWidth(50);
                rectList.get(i*5+j).setHeight(50);
            }
        }
        rectList.get(game.getPlayerX()*5+game.getPlayerY()).setFill(Color.TURQUOISE);

    }

    public void initdata(int modulo, String userName) {
        this.modulo = modulo;
        this.userName = userName;
    }

    @FXML
    private void handleOnKeyPressed (KeyEvent keyEvent) throws IOException{
        textList.get(game.getPlayerX()*5+game.getPlayerY()).setTextFill(Color.WHITE);
        rectList.get(game.getPlayerX()*5+game.getPlayerY()).setFill(Color.CORNFLOWERBLUE);
        int fieldValue = Integer.parseInt(textList.get(game.getPlayerX()*5+game.getPlayerY()).getText());
        boolean stepKeyPressed = true;
        if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            game.setPlayerY(floorMod(game.getPlayerY() + fieldValue, 5));
        } else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
            game.setPlayerY(floorMod(game.getPlayerY() - fieldValue, 5));
        } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            game.setPlayerX(floorMod(game.getPlayerX() - fieldValue, 5));
        } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            game.setPlayerX(floorMod(game.getPlayerX() + fieldValue, 5));
        } else {
            stepKeyPressed = false;
        }

        if(stepKeyPressed){
            steps++;
            numberOfSteps.setText("steps: " + steps);
        }

        textList.get(game.getPlayerX()*5+game.getPlayerY()).setTextFill(Color.RED);
        rectList.get(game.getPlayerX()*5+game.getPlayerY()).setFill(Color.TURQUOISE);
        game.updateTableValues(game.getPlayerX(), game.getPlayerY(), Integer.parseInt(textList.get(game.getPlayerX()*5+game.getPlayerY()).getText()), modulo);
        updateFieldValues();

        if(game.isSolved()){
            game.updateResults(userName, steps, modulo);

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/end.fxml"));
            Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    public void updateFieldValues(){
        for(int i=0; i<=4; i++){
            for(int j=0; j<=4; j++){
                textList.get(i*5+j).setText(String.valueOf(game.getElement(i,j)));
                textList.get(i*5+j).setTextFill(Color.WHITE);
            }
        }
        textList.get(game.getPlayerX()*5+game.getPlayerY()).setTextFill(Color.RED);
    }

    public void focus() {
        thePane.requestFocus();
    }
}
