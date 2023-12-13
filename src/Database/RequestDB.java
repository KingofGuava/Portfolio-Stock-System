package Database;

import Database.ClientDB;
import Client.*;

import java.sql.*;
import java.util.ArrayList;

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
            pstmt2.setFloat(2, 0);
            pstmt2.setFloat(3, 0);
            pstmt2.setFloat(4, 0);
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
    public void addStock(String symbol, double currentPrice) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false); // Start transaction

            // Prepare the SQL statement to insert the new stock
            String sqlInsert = "INSERT INTO ClientStock (username, symbol, currentprice, shares, buyinprice) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtInsert = c.prepareStatement(sqlInsert);

            // Set parameters for the insert statement
            pstmtInsert.setString(1, "market");
            pstmtInsert.setString(2, symbol);
            pstmtInsert.setDouble(3, currentPrice);
            pstmtInsert.setInt(4, 0); // Shares are 0 for a new stock entry
            pstmtInsert.setDouble(5, currentPrice); // buyinprice is set to currentprice

            // Execute the insert statement
            pstmtInsert.executeUpdate();
            pstmtInsert.close();

            // Commit the transaction
            c.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if an error occurs
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (c != null) c.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteStock(String symbol) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false); // Start transaction

            // Prepare the SQL statement to delete the stock entry
            String sqlDelete = "DELETE FROM ClientStock WHERE username = ? AND symbol = ?";
            PreparedStatement pstmtDelete = c.prepareStatement(sqlDelete);

            // Set parameters for the delete statement
            pstmtDelete.setString(1, "market");
            pstmtDelete.setString(2, symbol);

            // Execute the delete statement
            int rowsAffected = pstmtDelete.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock successfully deleted.");
            } else {
                System.out.println("Stock with symbol " + symbol + " not found for market.");
            }
            pstmtDelete.close();

            // Commit the transaction
            c.commit();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if an error occurs
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (c != null) c.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateStock(String symbol, double newCurrentPrice) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false); // Start transaction

            // Update the currentprice in the ClientStock table
            String sqlUpdateStock = "UPDATE ClientStock SET currentprice = ? WHERE symbol = ?";
            PreparedStatement pstmtUpdateStock = c.prepareStatement(sqlUpdateStock);
            pstmtUpdateStock.setDouble(1, newCurrentPrice);
            pstmtUpdateStock.setString(2, symbol);
            pstmtUpdateStock.executeUpdate();
            pstmtUpdateStock.close();

            // Find all clients holding the stock and calculate the total change in unrealized profits
            String sqlSelectClients = "SELECT username, shares, buyinprice FROM ClientStock WHERE symbol = ?";
            PreparedStatement pstmtSelectClients = c.prepareStatement(sqlSelectClients);
            pstmtSelectClients.setString(1, symbol);
            ResultSet rs = pstmtSelectClients.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                int shares = rs.getInt("shares");
                double buyinPrice = rs.getDouble("buyinprice");
                double totalChange = (newCurrentPrice - buyinPrice) * shares;

                // Update the unrealized profits in the Client table
                String sqlUpdateClient = "UPDATE Client SET unrealizedprofits = unrealizedprofits + ? WHERE username = ?";
                PreparedStatement pstmtUpdateClient = c.prepareStatement(sqlUpdateClient);
                pstmtUpdateClient.setDouble(1, totalChange);
                pstmtUpdateClient.setString(2, username);
                pstmtUpdateClient.executeUpdate();
                pstmtUpdateClient.close();
            }
            rs.close();
            pstmtSelectClients.close();

            // Commit the transaction
            c.commit();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if an error occurs
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (c != null) c.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String[][] manageStocks() {
        ArrayList<String[]> stockList = new ArrayList<>();
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();

            // Prepare the SQL statement to select stocks
            String sqlSelect = "SELECT symbol, currentprice FROM ClientStock WHERE username = 'market'";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);

            // Process the result set and add to the list
            while (rs.next()) {
                String symbol = rs.getString("symbol");
                String currentPrice = String.valueOf(rs.getDouble("currentprice"));
                stockList.add(new String[]{symbol, currentPrice});
            }
            rs.close();
            stmt.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Convert the list to a 2D array before returning
        String[][] tableValues = new String[stockList.size()][2];
        for (int i = 0; i < stockList.size(); i++) {
            tableValues[i] = stockList.get(i);
        }
        return tableValues;
    }
    public String[][] manageCustomers() {
        ArrayList<String[]> customerList = new ArrayList<>();
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();

            // Prepare the SQL statement to select customer data
            String sqlSelect = "SELECT username, realizedprofits, unrealizedprofits FROM Client";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect);

            // Process the result set and add to the list
            while (rs.next()) {
                String username = rs.getString("username");
                String realizedProfits = String.valueOf(rs.getFloat("realizedprofits"));
                String unrealizedProfits = String.valueOf(rs.getFloat("unrealizedprofits"));
                customerList.add(new String[]{username, realizedProfits, unrealizedProfits});
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Convert the list to a 2D array before returning
        String[][] tableValues = new String[customerList.size()][3];
        for (int i = 0; i < customerList.size(); i++) {
            tableValues[i] = customerList.get(i);
        }
        return tableValues;
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

