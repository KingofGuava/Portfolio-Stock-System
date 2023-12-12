package LogInRegistration;

import Client.Client;
import Database.RequestDB;
import LogInRegistration.LogRegBase;

public class Register extends LogRegBase {
    private String email;
    private RequestDB requestDB;
    
    public Register(String username, String password, String email) {//////
        this.usernameEntry = username;
        this.passwordEntry = password;
        this.email = email;
        this.requestDB = new RequestDB();
    }
    
    public String getEmail() {
        return this.email;
    }

    @Override
    public void runLogReg() {
        Client newClient = new Client(getUsername(), getPassword(), getEmail());
        requestDB.addNewRequest(newClient);
    }
}
