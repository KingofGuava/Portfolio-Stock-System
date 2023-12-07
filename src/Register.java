public class Register extends LogRegBase {
    private String email;
    private RequestDB requestDB;
    
    public Register(String username, String email, String password) {
        super(username, password);
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