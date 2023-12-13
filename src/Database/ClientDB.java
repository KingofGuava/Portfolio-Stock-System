package Database;

import Client.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDB {
    //Attributes
    public static ArrayList<Client> clientDB = new ArrayList<Client>();

    public void initialization(){
        Connection c = null;

        try {
            c = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM Users WHERE role=?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, "client");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                System.out.println("my username is： " + username);
                System.out.println("my password is： " + password);
                System.out.println("my email is： " + email);
                System.out.println("my role is： " + role);

            }
            rs.close();
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
        System.out.println("Query done successfully");

        //clientDB.add(new Client.Client("Alex", "pwd1", "a@gmail.com"));
        //clientDB.add(new Client.Client("Berry", "pwd2", "b@gmail.com"));
        //clientDB.add(new Client.Client("Karen", "pwd3", "c@gmail.com"));
    }
    //Function
    public void print(){
        for(int i=0; i<clientDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + clientDB.get(i).getUserName() + " , Password: " + clientDB.get(i).getPassword() + " , Email: " + clientDB.get(i).getEmail());
            clientDB.get(i).print();
        }
    }
    public void printNameOnly(){
        for(int i=0; i<clientDB.size(); i++){
            System.out.println("[" + i + "] " + "Username: " + clientDB.get(i).getUserName() + " , Password: " + clientDB.get(i).getPassword() + " , Email: " + clientDB.get(i).getEmail());
        }
    }
    //Check whether there is a registered client account matches the username entry and password entry
    public static boolean clientisRegistered(String username, String password) {
            Connection c = null;
            try {
                c = DatabaseConnection.getConnection();

                String sql = "SELECT * FROM Users WHERE role=? AND username=? AND password=?";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, "client");
                pstmt.setString(2, username);
                pstmt.setString(3, password);

                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    rs.close();
                    return true;
                }
                else {
                    rs.close();
                    return false;
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
            System.out.println("Login successfully");
        return false;
    }
    public static boolean managerisRegistered(String username, String password) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM Users WHERE role=? AND username=? AND password=?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, "manager");
            pstmt.setString(2, username);
            pstmt.setString(3, password);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                rs.close();
                return true;
            }
            else {
                rs.close();
                return false;
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
        System.out.println("Login successfully");
        return false;
    }

    //Same thing as the above function. The only difference is this returns the client
    public static Client getClient(String username, String password) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();

            String sql = "SELECT u.username, u.password, u.email, c.accountBalance, c.realizedprofits, c.unrealizedprofits " +
                    "FROM users u JOIN client c ON u.username = c.username " +
                    "WHERE u.role = ? AND u.username = ? AND u.password = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, "client");
            pstmt.setString(2, username);
            pstmt.setString(3, password);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String username1 = rs.getString("username");
                String password1 = rs.getString("password");
                String email1 = rs.getString("email");
                double accountBalance = rs.getDouble("accountBalance");
                double realizedProfits = rs.getDouble("realizedprofits");
                double unrealizedProfits = rs.getDouble("unrealizedprofits");

                Client client = new Client(username1, password1, email1, accountBalance, realizedProfits, unrealizedProfits);
                return client;
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                DatabaseConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }//k
        }
        System.out.println("Login successfully");
        return new Client();
        //
    }

    public ArrayList<Client> getDB(){
        return this.clientDB;
    }
    
    //Add one new Client.Client to the database
    public void addNewClient(Client client) {
        clientDB.add(client);
    }
}
