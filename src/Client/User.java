package Client;

/**
 * Created by NikolajK�mpe on 27-10-2015.
 */
public class User
{
    private String name = "name";
    private String ID = "";

    public User(String username, String ID)
    {
        name = username;
        this.ID = ID;;
    }

    public String getName(){return name;}

    public String getID() {return ID;}
}
