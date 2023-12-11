import java.util.ArrayList;

public class ClientDB {
    //Attributes
    private static ArrayList<Client> clientDB = new ArrayList<Client>();
    static{
        clientDB.add(new Client("Alex", "pwd1", "a@gmail.com"));
        clientDB.add(new Client("Berry", "pwd2", "b@gmail.com"));
        clientDB.add(new Client("Karen", "pwd3", "c@gmail.com"));
    }

    //Function
    public void print(){
        for(int i=0; i<clientDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + clientDB.get(i).getName() + " , Password: " + clientDB.get(i).getPassword() + " , Email: " + clientDB.get(i).getEmail());
            clientDB.get(i).print();
        }
    }
    public void printNameOnly(){
        for(int i=0; i<clientDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + clientDB.get(i).getName() + " , Password: " + clientDB.get(i).getPassword() + " , Email: " + clientDB.get(i).getEmail());
        }
    }
    //Check whether there is a registered client account matches the username entry and password entry
    public static boolean isRegistered(String username, String password) {
        for(Client registeredClient : clientDB) {
            if (registeredClient.getName().equalsIgnoreCase(username)
            &&  registeredClient.getPassword().equalsIgnoreCase(password)) {
                return true;
            }
        }
        return false;
    }

    //Same thing as the above function. The only difference is this returns the client
    public static Client getClient(String username, String password) {
        for(Client registeredClient : clientDB) {
            if (registeredClient.getName().equalsIgnoreCase(username)
            &&  registeredClient.getPassword().equalsIgnoreCase(password)) {
                return registeredClient;
            }
        }
        return new Client();
    }

    public ArrayList<Client> getDB(){
        return this.clientDB;
    }
    
    //Add one new Client to the database
    public void addNewClient(Client client) {
        clientDB.add(client);
    }
}
