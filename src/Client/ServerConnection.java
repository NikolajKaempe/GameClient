package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by NikolajKæmpe on 27-10-2015.
 */

// test af steffen
public class ServerConnection extends Thread
{
    // Socket fields
    private Socket clientSocket;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private String clearTextFromServer;
    private String[] textFromServer;
    private String username = "anonymous";
    private String id = "";
    private Mediator medi;
    private boolean running = true;

    public ServerConnection(Mediator medi){
        this.medi = medi;
    }

    /**
     * @author EmilMadsen & NikolajKæmpe
     * This method tries to connect to the server.
     * If this is possible, it will open the FrontPageGUI
     * Otherwise it will inform the user on the LoginGUI that a connect wasent possible
     */
    public void tryConnection( String name)
    {
        try {
            username = name;
            clientSocket = new Socket("localhost", 9898);

            outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject("000|" + username);
            outToServer.flush();

            inFromServer = new ObjectInputStream(clientSocket.getInputStream());
            clearTextFromServer = (String) inFromServer.readObject();

            textFromServer = clearTextFromServer.split("\\|");

            if (textFromServer[0].equals("008"))
            {
                String currentText = textFromServer[1];
                textFromServer = currentText.split(",");

                id = textFromServer[0];
                medi.setID(id);
                System.out.println("\nId = "+id);

                medi.activateFrontPageGui();
                System.out.println("WE HAVE CONNECTED");
                StatusListener statusListener = new StatusListener(medi, inFromServer);
                statusListener.start();
            }

            else if (textFromServer[0].equals("007"))
            {
                medi.setWarningLabel("Username not avaliable");
            }
            else
            {
                System.out.println("Didn't connect");
                medi.setWarningLabel("Unable to connect to server");

            }
        } catch (IOException e) { medi.setWarningLabel("Unable to connect to server"); }
        catch (ClassNotFoundException cnfEx){  medi.setWarningLabel("Unable to connect to server"); }

    }

    /**
     * @author EmilMadsen & NikolajKæmpe
     * this methid tries to write to the server, to request an game invite to another chosen client
     * @param opponentID - The Id of the Opponent that is to be invited to a game.
     * @param gameType - The type of the game that client wish to play.
     */
    public void inviteClient(String opponentID, String gameType)
    {
        // Client ask server to be connected to another client
        try {
            outToServer.writeObject("005|"+opponentID+","+gameType+","+id);
            System.out.println("005|"+opponentID+","+gameType+","+id);
            outToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author EmilMadsen & NikolajKæmpe
     * This method calls the server, to accept an invite from another client
     * @param afID - the id of the client who sent the game invite
     * @param moID - the od of this client, who have been invited
     * @param gameType - the type of the game requested to be played
     */
    public void acceptInvite(String afID, String moID, String gameType){
        // Client writes to server to accept.
        try {
            outToServer.writeObject("009|"+afID+","+moID+","+gameType);
            outToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author EmilMadsen & NikolajKæmpe
     * This method calls the server, to decline an invite from another client
     * @param afID - the id of the client who sent the game invite
     */
    public void declineInvite(String afID){
        // Client writes to server to decline.
        try {
            outToServer.writeObject("010|"+afID);
            outToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move()
    {
        // Calls the server with the current move.
    }

    public void exitGame()
    {
        // Disconnect from current game. Send info til server.
        // activate FrontPageGUI;
    }

    /**
     * This method listens to the server in a thread.
     * Depending on the status code, it will call the right method in the Mediator class.
     */

    public void run() {
        while (running) {

            try {

                //System.out.println("test1223332");
                clearTextFromServer = (String) inFromServer.readObject();

                textFromServer = clearTextFromServer.split("\\|");

                System.out.println(textFromServer[0]);

                switch (textFromServer[0])
                {
                    case "001" : // Forladt spil;
                        break;
                    case "002" : // Vundet
                        break;
                    case "003" : // tabt ;
                        break;
                    case "004" : // Træk
                        break;
                    case "005" : medi.serverRequest5(textFromServer[1]);// Inviter ;
                        break;
                    case "006" : medi.serverRequest6(textFromServer[1]); // SpillerListe
                        break;
                    case "007" : // Brugernavn optaget | Håndteres under oprettetsen;
                        break;
                    case "008" : // Forbindelse oprettet - Håndteres under oprettetsen
                        break;
                    case "009" :
                        //System.out.println("accepter invitation test"); // Accepter Invitation
                        medi.serverRequest9(textFromServer[1]);
                        break;
                }
                //System.out.println(this.isAlive());
            }catch (SocketException socEx){
                running = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
