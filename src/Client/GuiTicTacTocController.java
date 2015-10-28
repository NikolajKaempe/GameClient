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
public class GuiTicTacTocController implements Initializable {

    @FXML
    private Label lbl_leftName;

    @FXML
    private Label lbl_rightName;

    @FXML
    private Label label_leftScore;

    @FXML
    private Label label_rightScore;

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

    public GuiTicTacTocController(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        btn_1.setOnAction(event -> {
        });

        Image image = new Image(getClass().getResourceAsStream("cross.png"));
        btn_1.setGraphic(new ImageView(image));

        btn_2.setOnAction(event -> {
        });

        Image image2 = new Image(getClass().getResourceAsStream("circle.png"));
        btn_2.setGraphic(new ImageView(image2));

        btn_3.setOnAction(event -> {
        });
        btn_4.setOnAction(event -> {
        });
        btn_5.setOnAction(event -> {
        });
        btn_6.setOnAction(event -> {
        });
        btn_7.setOnAction(event -> {
        });
        btn_8.setOnAction(event -> {
        });
        btn_9.setOnAction(event -> {
        });

        btn_playAgain.setOnAction(event -> {
        });

        btn_quitGame.setOnAction(event -> {
            System.exit(0);
            Platform.exit();
        });

    }



}
