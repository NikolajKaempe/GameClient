package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by NikolajK�mpe on 27-10-2015.
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

    public void tryConnection( String name)
    {
        try {
            username = name;
            clientSocket = new Socket("10.111.176.157", 5556);

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
                this.run();

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


    public void inviteClient(String opponentID, String gameType)
    {
        // Client ask server to be connected to another client
        try {
            outToServer.writeObject("005|"+opponentID+","+gameType+","+id);
            outToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void acceptInvite(String afID, String moID, String gameType){
        // Client writes to server to accept.
        try {
            outToServer.writeObject("009|"+afID+","+moID+","+gameType);
            outToServer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void run()
    {
        while(running)
        {
            try {
                clearTextFromServer = (String) inFromServer.readObject();

                textFromServer = clearTextFromServer.split("\\|");

                switch (textFromServer[0])
                {
                    case "001" : // Forladt spil;
                        break;
                    case "002" : // Vundet
                        break;
                    case "003" : // tabt ;
                        break;
                    case "004" : // Tr�k
                        break;
                    case "005" : medi.serverRequest5(textFromServer[1]);// Inviter ;
                        break;
                    case "006" : medi.serverRequest6(textFromServer[1]); // SpillerListe
                        break;
                    case "007" : // Brugernavn optaget | H�ndteres under oprettetsen;
                        break;
                    case "008" : // Forbindelse oprettet - H�ndteres under oprettetsen
                        break;
                    case "009" : // Accepter Invitation
                        break;
                }

            }catch (SocketException socEx){running = false;}
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}