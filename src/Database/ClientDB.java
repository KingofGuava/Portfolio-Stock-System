package Database;

import Client.*;
import Manager.Stock;

import java.sql.*;
import java.util.ArrayList;

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
    public void buystocks(Client client, Stock stock, int sharesToBuy) {
        Connection c = null;
        int totalShares=0;
        double averageBuyInPrice=0;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false); // Start transaction

            // Step 1: Get the current price of the stock from the ClientStock table for 'market'
            String sqlSelectMarket = "SELECT currentprice FROM ClientStock WHERE username = ? AND symbol = ?";
            PreparedStatement pstmtSelectMarket = c.prepareStatement(sqlSelectMarket);
            pstmtSelectMarket.setString(1, "market");
            pstmtSelectMarket.setString(2, stock.getSymbol());
            ResultSet rsMarket = pstmtSelectMarket.executeQuery();

            double currentPrice = 0;
            if (rsMarket.next()) {
                currentPrice = rsMarket.getDouble("currentprice");
            }
            rsMarket.close();
            pstmtSelectMarket.close();

            // Step 2: Check if the client already has the stock
            String sqlSelectClient = "SELECT shares, buyinprice FROM ClientStock WHERE username = ? AND symbol = ?";
            PreparedStatement pstmtSelectClient = c.prepareStatement(sqlSelectClient);
            pstmtSelectClient.setString(1, client.getUserName());
            pstmtSelectClient.setString(2, stock.getSymbol());
            ResultSet rsClient = pstmtSelectClient.executeQuery();

            if (rsClient.next() && rsClient.getInt("shares") > 0) {
                // Client already has stock, calculate new average buyinprice
                int existingShares = rsClient.getInt("shares");
                double existingBuyInPrice = rsClient.getDouble("buyinprice");
                double totalCost = existingBuyInPrice * existingShares + currentPrice * sharesToBuy;
                totalShares = existingShares + sharesToBuy;
                averageBuyInPrice = totalCost / totalShares;

                // Update the existing stock with new buyinprice and shares
                String sqlUpdate = "UPDATE ClientStock SET shares = ?, buyinprice = ?, currentprice = ? WHERE username = ? AND symbol = ?";
                PreparedStatement pstmtUpdate = c.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, totalShares);
                pstmtUpdate.setDouble(2, averageBuyInPrice);
                pstmtUpdate.setDouble(3, currentPrice);
                pstmtUpdate.setString(4, client.getUserName());
                pstmtUpdate.setString(5, stock.getSymbol());
                pstmtUpdate.executeUpdate();
                pstmtUpdate.close();
            } else {
                // Client does not have stock, insert new stock for the client
                String sqlInsert = "INSERT INTO ClientStock (username, symbol, currentprice, shares, buyinprice) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmtInsert = c.prepareStatement(sqlInsert);
                pstmtInsert.setString(1, client.getUserName());
                pstmtInsert.setString(2, stock.getSymbol());
                pstmtInsert.setDouble(3, currentPrice);
                pstmtInsert.setInt(5, sharesToBuy);
                pstmtInsert.setDouble(4, currentPrice);
                pstmtInsert.executeUpdate();
                pstmtInsert.close();
            }
            rsClient.close();
            pstmtSelectClient.close();

            // Step 3: Update the client's account balance and unrealized profit in the Client table
            String sqlUpdateClient = "UPDATE Client SET accountbalance = accountbalance - ?, unrealizedprofits = unrealizedprofits + ? WHERE username = ?";
            PreparedStatement pstmtUpdateClient = c.prepareStatement(sqlUpdateClient);
            double cost = currentPrice * sharesToBuy;
            pstmtUpdateClient.setDouble(1, cost);

            // Assuming you have a method to calculate the new unrealized profit
            double newUnrealizedProfit = calculateUnrealizedProfit(client, stock, totalShares, averageBuyInPrice, currentPrice);
            pstmtUpdateClient.setDouble(2, newUnrealizedProfit);

            pstmtUpdateClient.setString(3, client.getUserName());
            pstmtUpdateClient.executeUpdate();
            pstmtUpdateClient.close();

            // Commit the transaction
            c.commit();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if an error occurs
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (c != null) c.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to calculate new unrealized profit - placeholder for actual calculation
    private double calculateUnrealizedProfit(Client client, Stock stock, int totalShares, double averageBuyInPrice, double marketPrice) {
        // This is a placeholder for the actual calculation.
        // You need to implement this based on your specific requirements.
        return (marketPrice - averageBuyInPrice) * totalShares;
    }
    public void sellstocks(Client client, Stock stock, int sharesToSell) {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
            c.setAutoCommit(false); // Start transaction

            // Step 1: Get the current price of the stock from the ClientStock table for 'market'
            String sqlSelect = "SELECT currentprice FROM ClientStock WHERE username = ? AND symbol = ?";
            PreparedStatement pstmtSelect = c.prepareStatement(sqlSelect);
            pstmtSelect.setString(1, "market");
            pstmtSelect.setString(2, stock.getSymbol());
            ResultSet rs = pstmtSelect.executeQuery();

            double currentPrice = 0;
            if (rs.next()) {
                currentPrice = rs.getDouble("currentprice");
            }
            rs.close();
            pstmtSelect.close();

            // Step 2: Update the client's shares in the ClientStock table
            String sqlUpdateShares = "UPDATE ClientStock SET shares = shares - ? WHERE username = ? AND symbol = ?";
            PreparedStatement pstmtUpdateShares = c.prepareStatement(sqlUpdateShares);
            pstmtUpdateShares.setInt(1, sharesToSell);
            pstmtUpdateShares.setString(2, client.getUserName());
            pstmtUpdateShares.setString(3, stock.getSymbol());
            pstmtUpdateShares.executeUpdate();
            pstmtUpdateShares.close();

            // Step 3: Update the client's account balance, unrealizedprofit, and realizedprofit in the Client table
            String sqlUpdateClient = "UPDATE Client SET accountbalance = accountbalance + ?, unrealizedprofit = unrealizedprofit - ?, realizedprofit = realizedprofit + ? WHERE username = ?";
            PreparedStatement pstmtUpdateClient = c.prepareStatement(sqlUpdateClient);
            double transactionAmount = currentPrice * sharesToSell;
            pstmtUpdateClient.setDouble(1, transactionAmount);
            pstmtUpdateClient.setDouble(2, transactionAmount);
            pstmtUpdateClient.setDouble(3, transactionAmount);
            pstmtUpdateClient.setString(4, client.getUserName());
            pstmtUpdateClient.executeUpdate();
            pstmtUpdateClient.close();

            // Commit the transaction
            c.commit();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            try {
                if (c != null) c.rollback(); // Roll back the transaction if an error occurs
            } catch (SQLException ex) {
                System.err.println("SQLException on rollback: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (c != null) c.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public String[][] display(Client client) {
        Connection c = null;
        ArrayList<String[]> marketStocksList = new ArrayList<>();
        try {
            c = DatabaseConnection.getConnection();

            // Step 1: Get all stocks for 'market'
            String sqlMarket = "SELECT symbol, currentprice, shares, buyinprice FROM ClientStock WHERE username = 'market'";
            Statement stmtMarket = c.createStatement();
            ResultSet rsMarket = stmtMarket.executeQuery(sqlMarket);

            while (rsMarket.next()) {
                String symbol = rsMarket.getString("symbol");
                String currentPrice = rsMarket.getString("currentprice");
                String shares = rsMarket.getString("shares");
                String buyinPrice = rsMarket.getString("buyinprice");
                marketStocksList.add(new String[]{symbol, currentPrice, shares, buyinPrice});
            }
            rsMarket.close();
            stmtMarket.close();

            // Step 2: Get all stocks for the provided client
            String sqlClient = "SELECT symbol, currentprice, shares, buyinprice FROM ClientStock WHERE username = ?";
            PreparedStatement pstmtClient = c.prepareStatement(sqlClient);
            pstmtClient.setString(1, client.getUserName());
            ResultSet rsClient = pstmtClient.executeQuery();

            // Step 3: Update marketStocksList with the client's stock information if there is a match on symbol
            while (rsClient.next()) {
                String symbol = rsClient.getString("symbol");
                String currentPrice = rsClient.getString("currentprice");
                String shares = rsClient.getString("shares");
                String buyinPrice = rsClient.getString("buyinprice");

                for (int i = 0; i < marketStocksList.size(); i++) {
                    String[] marketStock = marketStocksList.get(i);
                    if (marketStock[0].equals(symbol)) {
                        marketStocksList.set(i, new String[]{symbol, currentPrice, shares, buyinPrice});
                        break;
                    }
                }
            }
            rsClient.close();
            pstmtClient.close();

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
        String[][] marketStocksArray = new String[marketStocksList.size()][];
        return marketStocksList.toArray(marketStocksArray);
    }

    public ArrayList<Client> getDB(){
        return this.clientDB;
    }
    
    //Add one new Client.Client to the database
    public void addNewClient(Client client) {
        clientDB.add(client);
    }
}
