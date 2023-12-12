package LogInRegistration;

import Client.*;

import Database.ClientDB;
import GUI.CustomManagementPage;

public class LogIn extends LogRegBase {

    //Constructor
    public LogIn(String username, String password){
        super(username, password);
    }

    //Function
    @Override
    public void runLogReg() {
        new CustomManagementPage();
        /*if(ClientDB.isRegistered(getUsername(), getPassword())) {
            Client newClient = ClientDB.getClient(getUsername(), getPassword());
            newClient.Run();
        }else {
            //put error handling here in time.
            System.out.println("User is not registered");
        }*/
        
        
    }

}
