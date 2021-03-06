package Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Steffen on 27/10/15.
 */
public class GuiFrontpageController implements Initializable {

    @FXML
    private ListView list_players;

    @FXML
    private ToggleButton btn_tickTackToe;

    @FXML
    private ToggleButton btn_walkingDead;

    @FXML
    private ToggleButton btn_wow;

    @FXML
    private ToggleButton btn_yatzy;

    @FXML
    private Button btn_invite;

    final ToggleGroup toggleGroup = new ToggleGroup();

    private Stage primaryStage;
    private Mediator medi;

    public GuiFrontpageController(Stage stage, Mediator medi) {
        this.primaryStage = stage;
        this.medi = medi;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        btn_tickTackToe.setToggleGroup(toggleGroup);
        btn_walkingDead.setToggleGroup(toggleGroup);
        btn_wow.setToggleGroup(toggleGroup);
        btn_yatzy.setToggleGroup(toggleGroup);


        //set game names to togglebuttons
        btn_tickTackToe.setUserData("tictactoe");
        btn_walkingDead.setUserData("Game 2");
        btn_wow.setUserData("Game 3");
        btn_yatzy.setUserData("Game 4");





        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            public void changed(ObservableValue<? extends Toggle> observable, final Toggle oldValue, final Toggle newValue) {
                if ((newValue == null)) {
                    Platform.runLater(new Runnable() {

                        public void run() {
                            toggleGroup.selectToggle(oldValue);
                        }
                    });
                }
            }
        });

        // Demo data for listview
        // Demo

        //make sure a player and game is selected
        btn_invite.setOnAction(event -> {


            if(list_players.getSelectionModel().getSelectedItem()!=null)
            {
                //get selected togglebutton

                //System.out.println("Du har valgt spiller: " +list_players.getSelectionModel().getSelectedItem().toString() +"\nDu har valgt at spille: "+toggleGroup.getSelectedToggle().getUserData());
                String gameType = (String) toggleGroup.getSelectedToggle().getUserData();
                int opponentID = list_players.getSelectionModel().getSelectedIndex();
                String selectedPlayer = list_players.getSelectionModel().getSelectedItem().toString();




                if(!medi.getId().equals(medi.findIdByUseranme(selectedPlayer))) {
                    //System.out.println("invite sent to player");
                    medi.inviteClient(opponentID, gameType);
                }
                // add items to listview
            }
            else
            {
                System.out.println("Inner Check works");
            }

        });

    }

    public void updateUserList(ArrayList<User> userList)
    {


        String[] usernames = new String[userList.size()];


        for(int i = 0; i < usernames.length;i++)
        {
            usernames[i] = userList.get(i).getName();
        }

        ObservableList<String> items = FXCollections.observableArrayList(usernames);

        Platform.runLater(new Runnable() {

            public void run() {
                list_players.setItems(items);
            }
        });

    }
}
