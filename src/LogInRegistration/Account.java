package LogInRegistration;

import Database.ClientDB;
import Database.RequestDB;
import Manager.*;

import java.util.ArrayList;

public abstract class Account {
    //Attributes
    protected int ID;
    protected String password;
    protected String username;
    protected String email;
    protected Market market;
    protected RequestDB requestDB;
    protected ClientDB clientDB;
    protected ArrayList<String> receivedNotification;

    //Constructor
    public Account() {
        this.ID = 0;
        this.password = "";
        this.username = "";
        this.email = "";
        this.market = new Market();
        this.requestDB = new RequestDB();
        this.clientDB = new ClientDB();
        this.receivedNotification=new ArrayList<>();
    }

    //Access Func
    public int getID(){ return this.ID; }
    public String getPassword() { return this.password; }
    public String getUserName() { return this.username; }
    public String getEmail() { return this.email; }
    public ClientDB getClientDB() { return this.clientDB; }

    //Mutator Func
    public void setID(int id) { this.ID = id; }
    public void setPassword(String pwd) { this.password = pwd;}
    public void setUserName(String Name) { this.username = Name;}
    public void setEmail(String Email) { this.email = Email;}
}
