package Client;

import javafx.application.Application;
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
    public void activateGameGui()
    {

    }

    public void serverRequest1(String message)
    {

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
        if(!inGame)
        {
            String[] invInfo = message.split(",");
            String opponentName = findUsername(invInfo[2]);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Invite!");
            alert.setHeaderText("Player " + opponentName + " have challenged you to a game of " + invInfo[1] + ".");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                serverConnection.acceptInvite(invInfo[2], invInfo[0], invInfo[1]);
                inGame = true;
                // TODO Open Game
            } else {
                // Return to lobby
                serverConnection.declineInvite(invInfo[2]);
                //TODO return to lobby by closing AlertPane?
            }
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
            System.out.println(tempInfo[0]);
            String[] userInfo = tempInfo[i].split("\\.");

            if(!userInfo[0].equals(id))
            {
                userList.add(new User(userInfo[1],userInfo[0]));
            }
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
