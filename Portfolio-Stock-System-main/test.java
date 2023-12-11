package backend;
import java.sql.*;

public class test {
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:E:\\JetBrains\\java project\\finalproject\\src\\backend\\user.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sql = "INSERT INTO Users(username, password,name,role) VALUES(?,?,?,?)";
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setString(1, "ussername2");
            pstmt.setString(2, "123");
            pstmt.setString(3, "name1");
            pstmt.setString(4, "user");
            pstmt.executeUpdate();

            //String sql1 = "SELECT * FROM Users;";
            //pstmt = c.prepareStatement(sql1);
            //ResultSet rs = pstmt.executeQuery();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Users WHERE name= "+20 );

            while ( rs.next() ) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String role = rs.getString("role");
                System.out.println("my username is： "+username);
                System.out.println("my password is： "+password);
                System.out.println("my name is： "+name);
                System.out.println("my role is： "+role);

            }
            rs.close();
            //stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}


