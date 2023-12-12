package Client;

import LogInRegistration.Account;
import Manager.*;
import Database.DatabaseConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;

public class Client extends Account implements StockObserver {
    //Attributes
    private double balance;
    private double unreaProfit;
    private double reaProfit;
    private StockInventory ownedStock;
    private ArrayList<Double> shareStock;

    private Scanner sc;

    //Constructor
    public Client() {}
    public Client(String username, String password, String email){
        this.balance = 0;
        this.unreaProfit = 0; //flex
        this.reaProfit = 0;
        this.ownedStock = new StockInventory();
        this.shareStock = new ArrayList<Double>();
        this.username = username; //
        this.password = password;
        this.email = email;
    }
    public Client(String username, String password, String email,double balance,double unreaProfit,double reaProfit){
        this.balance = balance;
        this.unreaProfit = unreaProfit; //flex
        this.reaProfit = reaProfit;
        this.ownedStock = new StockInventory();
        this.shareStock = new ArrayList<Double>();
        this.username = username; //
        this.password = password;
        this.email = email;
    }
    public void fetchclient(){
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM Client WHERE username=?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, this.username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                this.username = rs.getString("username");
                this.balance = rs.getDouble("accountBalance");
                this.reaProfit = rs.getDouble("realizedprofits");
                this.unreaProfit = rs.getDouble("unrealizedprofits");
//                System.out.println("my username is： " + username);
//                System.out.println("my password is： " + password);
//                System.out.println("my email is： " + email);
//                System.out.println("my role is： " + role);

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
    }

    //Function
    public void Run(){
        this.fetchclient();
        //Manager.Market market = new Manager.Market();
        while(true){
            sc = new Scanner(System.in);
            System.out.println("Please decide your action by pressing a number from 1 to 5 ");
            int action = sc.nextInt();
            if(action==1) {         //Client.Buy

            }
            else if(action==2) {    //Client.Sell

            }
            else if(action==3) {    //Client.Deposit

            }
            else if(action==4) {    //Client.Withdraw

            }
            else if(action==5) {    //Check unrealized profit
                CheckUnreaProfit();
            }
            else if(action==6) {    //Check realized profit
                CheckReaProfit();
            }

        }
    }






    public void CheckUnreaProfit(){
        System.out.println("Your unrealized profit is: " + this.unreaProfit);
    }

    public void CheckReaProfit(){
        System.out.println("Your realized profit is: " + this.reaProfit);
    }

    public void addNotification(String msg){
        this.receivedNotification.add(msg);
    }

    public void print(){
        System.out.println("UserName: " + username);
        System.out.println("Balance: " + balance + " | Unrealized Profit: " + unreaProfit + " | Realized Profit: " + reaProfit);
        ownedStock.displayStock();
        System.out.println("Below is the received notifications: ");
        for(int i=0; i<receivedNotification.size(); i++){
            System.out.println("[ " + i + " ] : " + receivedNotification.get(i));
        }
        System.out.println("-----------------------");
        System.out.println();
    }

    //implements the stockobserver interface, it will be notified once the stock is updated
    @Override
    public void update(String stockSymbol, double stockPrice) {
        System.out.println(username + " received an update for " + stockSymbol + ": $" + stockPrice);
    }

}
