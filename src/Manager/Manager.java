package Manager;

import Client.Client;
import LogInRegistration.Account;

import java.util.Scanner;

public class Manager extends Account {
    //Attributes
    private Scanner sc;

    //Constructor
    public Manager(){}

    //Functions
    public void Run(){
        //new LogInRegistration.LogRegPage().run();
        //Manager.Market market = new Manager.Market();
        while(true){
            sc = new Scanner(System.in);
            System.out.println("Please decide your action by pressing a number from 1 to 5 ");
            int action = sc.nextInt();
            if(action==1) {         //Add Manager.Stock
                market.printMarket();
                System.out.println("Please input the symbol of the stock you want to add.");
                String symbol = sc.next();
                System.out.println("Please intput the price of the stock you want to add.");
                double price = sc.nextDouble();
                Stock stockToAdd = new Stock(symbol, price, 10000, price);
                market.addStock(stockToAdd);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
            }
            else if(action==2) {    //Remove Manager.Stock
                market.printMarket();
                System.out.println("Please input the index of the stock you want to remove.");
                int index = sc.nextInt();
                market.removeStock(index);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
            }
            else if(action==3) {    //Modify Manager.Stock Price
                market.printMarket();
                System.out.println("Please input the index of the stock you want to modify.");
                int index = sc.nextInt();
                System.out.println("Please intput the price of the stock you want to set.");
                double price = sc.nextDouble();
                market.modifyStockPrice(index, price);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
                //invokes the notify function in observation pattern in MarketWatchList interface
                market.notifyObservers(market.getMarket().get(index).getSymbol(), price);
            }
            else if(action==4) {    //Track Client.Client Profits
                clientDB.print();
            }
            else if(action==5) {    //Notify Client.Client
                clientDB.printNameOnly();
                System.out.println("Please input the index of the Client.Client you want to notify.");
                int index = sc.nextInt();
                System.out.println("Please input the message you want to notify the client.");
                String msg = sc.next();
                clientDB.getDB().get(index).addNotification(msg);
                clientDB.getDB().get(index).print();
            }
            else if(action==6) {    //Approve the request
                requestDB.print();
                System.out.println("Which registeration request you would like to approve?");
                int index = sc.nextInt();
                clientDB.addNewClient(requestDB.getRequest(index));
                requestDB.removeRequest(index);
                requestDB.print();
            }
            else if(action==7) {    //Reject the request
                requestDB.print();
                System.out.println("Which registeration request you would like to reject?");
                int index = sc.nextInt();
                requestDB.removeRequest(index);
                requestDB.print();
            }
        }
    }

    public void approveNewClient(Client client){

    }

    public void trackClientProfits(){

    }

    public void notifyClient(){

    }
}
