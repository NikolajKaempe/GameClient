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

    Image image = new Image(getClass().getResourceAsStream("cross.png"));
    Image image2 = new Image(getClass().getResourceAsStream("circle.png"));


    public GuiTicTacToeController(Stage stage, Mediator mediator, String opponentId, String ownId) {
        this.primaryStage = stage;
        this.mediator = mediator;
        this.opponentId = opponentId;
        this.ownId = ownId;
        System.out.println("opp id : " + opponentId);
        System.out.println("own id: " + ownId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("test1: "+mediator.isInGame());

        lbl_leftName.setText(mediator.findUsername(ownId));
        lbl_rightName.setText(mediator.findUsername(opponentId));

        imageView_left.setImage(image);

        imageView_right.setImage(image2);

        btn_1.setOnAction(event -> {
            paintButtons(btn_1);
            disableButtons(btn_1);
        });
        btn_2.setOnAction(event -> {
            paintButtons(btn_2);
            disableButtons(btn_2);
        });
        btn_3.setOnAction(event -> {
            paintButtons(btn_3);
            disableButtons(btn_3);
        });
        btn_4.setOnAction(event -> {
            paintButtons(btn_4);
            disableButtons(btn_4);
        });
        btn_5.setOnAction(event -> {
            paintButtons(btn_5);
            disableButtons(btn_5);
        });
        btn_6.setOnAction(event -> {
            paintButtons(btn_6);
            disableButtons(btn_6);
        });
        btn_7.setOnAction(event -> {
            paintButtons(btn_7);
            disableButtons(btn_7);
        });
        btn_8.setOnAction(event -> {
            paintButtons(btn_8);
            disableButtons(btn_8);
        });
        btn_9.setOnAction(event -> {
            paintButtons(btn_9);
            disableButtons(btn_9);
        });

        btn_playAgain.setOnAction(event -> {

            System.out.println("333");
        });

        btn_quitGame.setOnAction(event -> {


            mediator.serverRequest1(opponentId);
        });



    }

    private void disableButtons(Button btn) {
        btn.setDisable(true);
        btn.setOpacity(1);
    }

    private void clearBoard() {

    }

    private void highlightWinStreak(int one, int two, int three){

        switch ( one ) {

        }

        btn_1.setStyle("-fx-base: #48e732;");
    }

    private void paintButtons(Button btn) {

        btn.setGraphic(new ImageView(image));
        btn.setGraphic(new ImageView(image2));
    }



}
