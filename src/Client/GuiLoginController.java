package Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Steffen on 27/10/15.
 */
public class GuiLoginController implements Initializable {

    @FXML
    private Button btn_connect;

    @FXML
    private Button btn_cancel;

    @FXML
    private Label lbl_nickname;

    @FXML
    private TextField txtfield_username;


    private Stage primaryStage;
    private Mediator medi;
    private String msg = "";

    public GuiLoginController(Stage stage, Mediator medi)
    {
        this.primaryStage = stage;
        this.medi = medi;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtfield_username.requestFocus();
            }
        });



        btn_connect.setOnAction(event -> {
            msg = txtfield_username.getText();

            if(!msg.equalsIgnoreCase("") && !msg.equalsIgnoreCase(null)) {


               medi.tryConnectToServer(msg);

            }
            else {
                lbl_nickname.setTextFill(Color.web("#FF0000"));
                lbl_nickname.setText("Du skal skrive et brugernavn!");
            }
        });

        btn_cancel.setOnAction(event -> {
            System.exit(0);
            Platform.exit();
        });



    }
    public void setLabelWarning(String message)
    {
        lbl_nickname.setTextFill(Color.web("#FF0000"));
        lbl_nickname.setText(message);
    }
}
