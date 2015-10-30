package Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Dion on 27-10-2015.
 */
public class GuiTicTacToeController implements Initializable {

    @FXML
    private Label lbl_leftName;

    @FXML
    private Label lbl_rightName;

    @FXML
    private Label label_leftScore;

    @FXML
    private Label label_rightScore;

    @FXML
    private ImageView imageView_left;

    @FXML
    private ImageView imageView_right;

    @FXML
    private Label label_whosTurn;

    @FXML
    private Button btn_1;

    @FXML
    private Button btn_2;

    @FXML
    private Button btn_3;

    @FXML
    private Button btn_4;

    @FXML
    private Button btn_5;

    @FXML
    private Button btn_6;

    @FXML
    private Button btn_7;

    @FXML
    private Button btn_8;

    @FXML
    private Button btn_9;

    @FXML
    private Button btn_quitGame;

    @FXML
    private Button btn_playAgain;

    private Stage primaryStage;

    private Mediator mediator;
    private String opponentId;
    private String ownId;
    private Boolean myTurn = false;
    private Button[] boardButtons = new Button[9];
    private int[] boardValues;

    Image image = new Image(getClass().getResourceAsStream("cross.png"));
    Image image2 = new Image(getClass().getResourceAsStream("circle.png"));
    private boolean gameEnded = false;
    private byte gameWinningStatus = 0;


    public GuiTicTacToeController(Stage stage, Mediator mediator, String opponentId, String ownId) {
        this.primaryStage = stage;
        this.mediator = mediator;
        this.opponentId = opponentId;
        this.ownId = ownId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lbl_leftName.setText(mediator.findUsername(ownId));
        lbl_rightName.setText(mediator.findUsername(opponentId));

        imageView_left.setImage(image);

        imageView_right.setImage(image2);

        boardButtons[0] = btn_1;
        boardButtons[1] = btn_2;
        boardButtons[2] = btn_3;
        boardButtons[3] = btn_4;
        boardButtons[4] = btn_5;
        boardButtons[5] = btn_6;
        boardButtons[6] = btn_7;
        boardButtons[7] = btn_8;
        boardButtons[8] = btn_9;

        // Set events on board buttons
        for(int i = 0; i < boardButtons.length; i++) {

            final int finalI = i;

            boardButtons[i].setOnAction(e -> {
                if(myTurn && boardValues[finalI]==0){
                    mediator.makeMove(finalI);
                }
            });


        }

        btn_playAgain.setOnAction(event -> {
        });

        btn_quitGame.setOnAction(event -> {
            mediator.activateFrontPageGui();
        });

    }
/*
    private void disableButtons(Button btn) {
        btn.setDisable(true);
        btn.setOpacity(1);
    }
*/

    public void updateGameBoard(String gameState) {

        boardValues = new int[9];
        String[] input = gameState.split(",");
        String turnID = input[0];
        String gameBody = input[1];


        if (turnID.equals(ownId) && !gameEnded) {
            myTurn = true;
            label_whosTurn.setText("It's your turn!");
        } else {
            myTurn = false;
            label_whosTurn.setText("It's your opponents turn!");
        }

        String[] positionValues = gameBody.split("\\.");

        for (int i = 0; i < positionValues.length; i++) {
            String s = ""+positionValues[i].charAt(2);
            int value = Integer.parseInt(s);
            if(value == 2) {
                boardValues[i] = 2;
                paintButtons(i,2);
            }
            else if (value == 1){
                boardValues[i] = 1;
                paintButtons(i,1);
            }
            else{
                boardValues[i] = 0;
                // Draw Nothing
            }

        }

        if( !gameEnded ) return;

        switch(gameWinningStatus) {
            case 1:
                label_whosTurn.setText("Congratulations, you've won!");
                break;
            case 2:
                label_whosTurn.setText("Sorry, you lost!");
                break;
            case 3:
                label_whosTurn.setText("It's a draw!");
        }

    }

    public void win() {
        Platform.runLater(() -> {
            gameEnded = true;
            gameWinningStatus = 1;
        });
    }

    public void lose() {
        Platform.runLater(() -> {
            gameEnded = true;
            gameWinningStatus = 2;
        });
    }

    public void draw() {
        Platform.runLater(() -> {
            gameEnded = true;
            gameWinningStatus = 3;
        });
    }


    private void highlightWinStreak(int one, int two, int three){

        btn_1.setStyle("-fx-base: #48e732;");
    }

    private void paintButtons(int btn, int type) {

        if (type == 1) {
            boardButtons[btn].setGraphic(new ImageView(image));

        } else if (type == 2) {
            boardButtons[btn].setGraphic(new ImageView(image2));
        } else {
            // do nothing
        }

    }

}
