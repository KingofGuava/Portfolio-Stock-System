package LogInRegistration;

import Client.Client;
import Database.RequestDB;
import GUI.CustomerLoginRegisterPage;
import LogInRegistration.LogRegBase;

import java.awt.*;

public class Register extends LogRegBase {
    private String fullname;
    private String phone;
    private String address;
    private String email;
    private RequestDB requestDB;
    
    public Register(String username, String password, String fullname, String phone, String address, String email) {
        this.usernameEntry = username;
        this.passwordEntry = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.requestDB = new RequestDB();
    }
    
    public String getEmail() {
        return this.email;
    }

    @Override
    public void runLogReg() {
        try{
            new CustomerLoginRegisterPage();
        } catch (AWTException ex) {
        throw new RuntimeException(ex);
        }

        Client newClient = new Client(getUsername(), getPassword(), getEmail());
        requestDB.addNewRequest(newClient);
    }
}
