import java.util.ArrayList;

public class RequestDB {
    //Attributes
    private static ArrayList<Client> requestDB = new ArrayList<Client>();
    static{
        requestDB.add(new Client("Alex", "pwd1", "a@gmail.com"));
        requestDB.add(new Client("Berry", "pwd2", "b@gmail.com"));
        requestDB.add(new Client("Karen", "pwd3", "c@gmail.com"));
    }

    //Function
    public void print(){
        for(int i=0; i<requestDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + requestDB.get(i).getName() + " , Password: " + requestDB.get(i).getPassword() + " , Email: " + requestDB.get(i).getEmail());
        }
    }
    
    //Add one new request to the database
    public void addNewRequest(Client client) {
        requestDB.add(client);
    }
    //Remove one request from the database
    public void removeRequest(int index) {
        requestDB.remove(index);
    }
    //Get one request from the database
    public Client getRequest(int index) {
        return requestDB.get(index);
    }
}

