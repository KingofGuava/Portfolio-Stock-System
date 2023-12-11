public abstract class LogRegBase {
    private String usernameEntry;
    private String passwordEntry;
    private ClientDB clientDB;
    
    protected LogRegBase(String username, String password) {
        this.clientDB = new ClientDB();//both forms are going to need access to the same database
        this.usernameEntry = username;
        this.passwordEntry = password;
    }
    
    public String getUsername() {
        return this.usernameEntry;
    }
    
    public String getPassword() {
        return this.passwordEntry;
    }
    
    public ClientDB getDataBase() {
        return this.clientDB;
    }
    
    public abstract void runLogReg();
}
