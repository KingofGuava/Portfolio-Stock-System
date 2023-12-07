public abstract class Account {
    //Attributes
    protected int ID;
    protected String password;
    protected String name;
    protected String email;
    protected Market market;
    protected RequestDB requestDB;
    protected ClientDB clientDB;

    //Constructor
    public Account() {
        this.ID = 0;
        this.password = "";
        this.name = "";
        this.email = "";
        this.market = new Market();
        this.requestDB = new RequestDB();
        this.clientDB = new ClientDB();
    }

    //Access Func
    public int getID(){ return this.ID; }
    public String getPassword() { return this.password; }
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public ClientDB getClientDB() { return this.clientDB; }

    //Mutator Func
    public void setID(int id) { this.ID = id; }
    public void setPassword(String pwd) { this.password = pwd;}
    public void setName(String Name) { this.name = Name;}
    public void setEmail(String Email) { this.email = Email;}
}
