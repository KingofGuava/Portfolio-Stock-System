package Database;

import Database.ClientDB;
import Client.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestDB {
    //Attributes
    private static ArrayList<Client> requestDB = new ArrayList<Client>();
//    static{
//        requestDB.add(new Client.Client("Alex", "pwd1", "a@gmail.com"));
//        requestDB.add(new Client.Client("Berry", "pwd2", "b@gmail.com"));
//        requestDB.add(new Client.Client("Karen", "pwd3", "c@gmail.com"));
//    }

    //Function
    public void print(){
        for(int i=0; i<requestDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + requestDB.get(i).getUserName() + " , Password: " + requestDB.get(i).getPassword() + " , Email: " + requestDB.get(i).getEmail());
        }
    }
    
    //Add one new request to the database
    public void addNewRequest(Client client) {
        Connection c = null;

        try {
            c = DatabaseConnection.getConnection();

            String sql1 = "INSERT INTO Users(username, password, email, role) VALUES(?,?,?,?)";
            PreparedStatement pstmt1 = c.prepareStatement(sql1);

            pstmt1.setString(1, client.getUserName());
            pstmt1.setString(2, client.getPassword());
            pstmt1.setString(3, client.getEmail());
            pstmt1.setString(4, "client");
            pstmt1.executeUpdate();
            ClientDB.clientDB.add(new Client(client.getUserName(), client.getPassword(), client.getEmail()));
            System.out.println("Users insertion successful");

            String sql2 = "INSERT INTO Client(username, accountBalance, realizedprofits, unrealizedprofits) VALUES(?,?,?,?)";
            PreparedStatement pstmt2 = c.prepareStatement(sql2);

            pstmt2.setString(1, client.getUserName());
            pstmt2.setString(2, "0");
            pstmt2.setString(3, "0");
            pstmt2.setString(4, "0");
            pstmt2.executeUpdate();

            c.commit();
        }
        catch (SQLException e) {
            // Catch all exceptions related to database operations
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if possible
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                DatabaseConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Operation done successfully");
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

