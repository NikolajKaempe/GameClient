package Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Steffen on 28/10/15.
 */
public class StatusListener extends Thread {

    private ObjectInputStream objectInputStream;

    private Mediator mediator;
    private String clearTextFromServer;
    private String[] textFromServer;
    boolean check = true;




    public StatusListener(Mediator medi, ObjectInputStream inFromServer) {
        this.mediator = medi;
        this.objectInputStream = inFromServer;
    }


    @Override
    public void run() {
        while (check) {

            try {

                //System.out.println("test1223332");
                clearTextFromServer = (String) objectInputStream.readObject();

                textFromServer = clearTextFromServer.split("\\|");

                System.out.println(textFromServer[0]);

                switch (textFromServer[0])
                {
                    case "001" : // Forladt spil;
                        break;
                    case "002" : // Vundet
                        System.out.println("heeeej");
                        mediator.serverRequest2();
                        break;
                    case "003" : // tabt
                        mediator.serverRequest3();
                        break;
                    case "004" : // Træk
                        break;
                    case "005" : mediator.serverRequest5(textFromServer[1]);// Inviter ;
                        break;
                    case "006" : mediator.serverRequest6(textFromServer[1]); // SpillerListe
                        break;
                    case "007" : // Brugernavn optaget | Håndteres under oprettetsen;
                        break;
                    case "008" : // Forbindelse oprettet - Håndteres under oprettetsen
                        break;
                    case "009" :
                        //System.out.println("accepter invitation test"); // Accepter Invitation
                        mediator.serverRequest9(textFromServer[1]);
                        break;
                    case "010" :
                        //System.out.println(textFromServer[1]);
                        mediator.serverRequest10(textFromServer[1]);
                        break;
                    case "011" :
                        mediator.serverRequest11(textFromServer[1]);
                        break;
                    case "012":
                        mediator.serverRequest12();
                        break;
                }
                //System.out.println(this.isAlive());
            }catch (SocketException socEx){
                check = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
