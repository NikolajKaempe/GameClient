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
    private ArrayList<Button> boardButtons;
    private int[] boardValues;

    Image image = new Image(getClass().getResourceAsStream("cross.png"));
    Image image2 = new Image(getClass().getResourceAsStream("circle.png"));


    public GuiTicTacToeController(Stage stage, Mediator mediator, String opponentId, String ownId) {
        this.primaryStage = stage;
        this.mediator = mediator;
        this.opponentId = opponentId;
        this.ownId = ownId;
        boardButtons = new ArrayList<Button>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lbl_leftName.setText(mediator.findUsername(ownId));
        lbl_rightName.setText(mediator.findUsername(opponentId));

        imageView_left.setImage(image);

        imageView_right.setImage(image2);

        boardButtons.add(btn_1);
        boardButtons.add(btn_2);
        boardButtons.add(btn_3);
        boardButtons.add(btn_4);
        boardButtons.add(btn_5);
        boardButtons.add(btn_6);
        boardButtons.add(btn_7);
        boardButtons.add(btn_8);
        boardButtons.add(btn_9);



        btn_1.setOnAction(event -> {
            if(myTurn && boardValues[0]==0){
                mediator.makeMove(0);
                System.out.println("Click!!");
            }
        });
        btn_2.setOnAction(event -> {
            if(myTurn && boardValues[1]==0){
                mediator.makeMove(1);
            }
        });
        btn_3.setOnAction(event -> {
            if(myTurn && boardValues[2]==0){
                mediator.makeMove(2);
            }
        });
        btn_4.setOnAction(event -> {
            if(myTurn && boardValues[3]==0){
                mediator.makeMove(3);
            }
        });
        btn_5.setOnAction(event -> {
            if(myTurn && boardValues[4]==0){
                mediator.makeMove(4);
            }
        });
        btn_6.setOnAction(event -> {
            if(myTurn && boardValues[5]==0){
                mediator.makeMove(5);
            }
        });
        btn_7.setOnAction(event -> {
            if(myTurn && boardValues[6]==0){
                mediator.makeMove(6);
            }
        });
        btn_8.setOnAction(event -> {
            if(myTurn && boardValues[7]==0){
                mediator.makeMove(7);
            }
        });
        btn_9.setOnAction(event -> {
            if(myTurn && boardValues[8]==0){
                mediator.makeMove(8);
            }
        });

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

        if (turnID.equals(ownId)) {
            myTurn = true;
        } else {
            myTurn = false;
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

    }


    private void highlightWinStreak(int one, int two, int three){

        btn_1.setStyle("-fx-base: #48e732;");
    }

    private void paintButtons(int btn, int type) {

        if (type == 1) {
            boardButtons.get(btn).setGraphic(new ImageView(image));

        } else if (type == 2) {
            boardButtons.get(btn).setGraphic(new ImageView(image2));
        } else {
            // do nothing
        }

    }



}
