package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by NikolajKæmpe on 26-10-2015.
 */

public class Mediator extends Application
{
        // Fields
    private Stage primaryStage;
    private GuiLoginController guiLoginController;
    private GuiFrontpageController guiFrontpageController;
    private GuiTicTacToeController guiTicTacToeController;
    private ServerConnection serverConnection;
    private ArrayList<User> userList;
    private String username;
    private String id;
    private boolean inGame = false;


    /**
     * @author EmilMadsen & NikolajKæmpe
     * This is the Constructor.
     * It initializes the needed information
     */
    public Mediator() {
        serverConnection = new ServerConnection(this);
        userList = new ArrayList<User>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        this.primaryStage = primaryStage;

        //close on exit
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        activateLoginGui();

    }

    public void tryConnectToServer(String username)
    {
        serverConnection.tryConnection(username);
        this.username = username;
    }

    public void setWarningLabel(String message)
    {
        guiLoginController.setLabelWarning(message);
    }

    public void activateLoginGui()
    {
        FXMLLoader loginLoader = new FXMLLoader();
        guiLoginController = new GuiLoginController(primaryStage,this);

        loginLoader.setController(guiLoginController);
        loginLoader.setLocation(getClass().getResource("clientLogin.fxml"));

            Parent root1 = null;
        try {
            root1 = loginLoader.load();
        } catch (IOException e) {e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
    }

    public void activateFrontPageGui()
    {
        FXMLLoader frontpageLoader = new FXMLLoader();

        guiFrontpageController = new GuiFrontpageController(primaryStage,this);

        frontpageLoader.setController(guiFrontpageController);
        frontpageLoader.setLocation(getClass().getResource("Frontpage.fxml"));

        Parent root2 = null;

        try {
            root2 = frontpageLoader.load();
        } catch (IOException e) {e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root2));

        primaryStage.show();

    }
    public void activateGameGui(String gametype, String opponentId, String ownId)
    {
        FXMLLoader tictactoeLoader = new FXMLLoader();

        switch (gametype) {

            case "tictactoe":
                guiTicTacToeController = new GuiTicTacToeController(primaryStage,this,opponentId,ownId);

                tictactoeLoader.setController(guiTicTacToeController);
                tictactoeLoader.setLocation(getClass().getResource("TicTacToe.fxml"));

                break;

        }

        if(tictactoeLoader.getLocation() == null || tictactoeLoader.getController() == null) return;

        Parent root3 = null;

        try {
            root3 = tictactoeLoader.load();
        } catch (IOException e) {e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root3));

        primaryStage.show();

    }

    public void serverRequest1(String message)
    {



        if(!inGame) return;
        System.out.println("Request 1");
        System.out.println(message);
        serverConnection.exitGame(message);
        inGame = false;


        Platform.runLater(new Runnable() {

            public void run() {


                activateFrontPageGui();
                guiFrontpageController.updateUserList(userList);
                if(!message.equals(id)) return;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You won the game");
                alert.setHeaderText(null);
                alert.setContentText("You won :)");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    //activateFrontPageGui();
                    //guiFrontpageController.updateUserList(userList);
                }

                else {
                    // do nothing
                }



            }

        });






    }
    public void serverRequest2(String message)
    {

    }

    public void serverRequest3(String message)
    {

    }
    public void serverRequest4(String message)
    {

    }

    public void serverRequest5(String message)
    {
        System.out.println("REQUEST 5");

        System.out.println("test3: " +isInGame());

        if(!inGame)
        {
            String[] invInfo = message.split(",");
            String opponentName = findUsername(invInfo[2]);

            Platform.runLater(new Runnable() {

                public void run() {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Game Invite!");
                    alert.setHeaderText("Player " + opponentName + " have challenged you to a game of " + invInfo[1] + ".");
                    alert.setContentText("Are you ok with this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        // 0 - afsender
                        // 1- mod
                        // 2 - spil
                        serverConnection.acceptInvite(invInfo[2], invInfo[0], invInfo[1]);
                        inGame = true;



                        //  0 gametype
                        //  1 opponent
                        //  2 ownid

                        activateGameGui(invInfo[1],invInfo[2],invInfo[0]);

                        // TODO Open Game
                    } else {
                        // Return to lobby
                        serverConnection.declineInvite(invInfo[0]);
                        //TODO return to lobby by closing AlertPane?
                    }
                }
            });


        }

    }
    public void serverRequest6(String message)
    {
        System.out.println("REQUEST 6");
        userList = new ArrayList<>();
        System.out.println(message);
        String[] tempInfo = message.split(",");

        for (int i = 0; i < tempInfo.length ; i++)
        {
            //System.out.println(tempInfo[0]);
            String[] userInfo = tempInfo[i].split("\\.");

            //if(!userInfo[0].equals(id))
            //{
                userList.add(new User(userInfo[1],userInfo[0]));
                //System.out.println("test " +userList.size());

            //}
        }


        if (inGame == false)
        {
            guiFrontpageController.updateUserList(userList);
        }
    }
    public void serverRequest7(String message)
    {

    }
    public void serverRequest8(String message)
    {

    }

     public void serverRequest9(String message)
     {

         String[] tempInfo = message.split(",");

         System.out.println("REQUEST 9");

         Platform.runLater(new Runnable() {

             public void run() {
                 inGame = true;
                 //  0 gametype
                 //  1 opponent
                 //  2 ownid

                 System.out.println("gametype: "+tempInfo[2]);
                 System.out.println("opponent: "+tempInfo[1]);
                 System.out.println("ownid: "+tempInfo[0]);
                 activateGameGui(tempInfo[2], tempInfo[1], tempInfo[0]);
             }
         });



     }

    public void serverRequest10(String message) {

        System.out.println("REQUEST 10");

        System.out.println(message);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Rejected!");
                alert.setHeaderText(null);
                alert.setContentText("Player "+ findUsername(message) +" have declined your invite.");
                alert.showAndWait();



        }
        });
    }

    public void serverRequest11(String message) {

        System.out.println("REQUEST 11");

        System.out.println(message);
    }

    public void inviteClient(int opponentListID, String gameType)
    {
        String opponentID = userList.get(opponentListID).getID();

        serverConnection.inviteClient(opponentID, gameType);
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public boolean isInGame() {
        return inGame;
    }

    public String getId() {
        return id;
    }


    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public String findUsername(String id)
    {
        for (User user : userList)
        {
            if(id.equals(user.getID()))
            {
                return user.getName();
            }
        }
        return "0";
    }

    public String findIdByUseranme(String username) {
        for (User user : userList) {
            if (username.equals(user.getName())) {
                return user.getID();
            }
        }
        return "0";
    }

    public static void main(String[] args)
    {
        Mediator m = new Mediator();
        m.startProgram();
    }
    private static void startProgram()
    {
        launch();
    }

}
