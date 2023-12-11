import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    private static Scanner sc;
    public static void main(String[] args) throws Exception {
        //Connect c=new Connect();
        Connection c = null;
        Statement stmt = null;
        c = DatabaseConnection.getConnection();

        //c.connecttodb();
        System.out.println("Press 1 for Manager or 2 for Client");
        sc = new Scanner(System.in);
        int loginDecision = sc.nextInt();
        if(loginDecision==1) {
            new Manager().Run();
        }
        else if(loginDecision==2) {
            new LogRegPage().run();
        }
    }
}
