import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Account {
    //Attributes
    private Scanner sc;
    private ArrayList<Client> clientPool;
    //Constructor
    public Manager(){}

    //Functions
    public void Run(){
        new LogIn().Run();
        Market market = new Market();
        while(true){
            sc = new Scanner(System.in);
            System.out.println("Please decide your action by pressing a number from 1 to 5 ");
            int action = sc.nextInt();
            if(action==1) {         //Add Stock
                market.printMarket();
                System.out.println("Please input the symbol of the stock you want to add.");
                String symbol = sc.next();
                System.out.println("Please intput the price of the stock you want to add.");
                double price = sc.nextDouble();
                Stock stockToAdd = new Stock(symbol, price);
                market.addStock(stockToAdd);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
            }
            else if(action==2) {    //Remove Stock
                market.printMarket();
                System.out.println("Please input the index of the stock you want to remove.");
                int index = sc.nextInt();
                market.removeStock(index);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
            }
            else if(action==3) {    //Modify Stock Price
                market.printMarket();
                System.out.println("Please input the index of the stock you want to modify.");
                int index = sc.nextInt();
                System.out.println("Please intput the price of the stock you want to set.");
                double price = sc.nextDouble();
                market.modifyStockPrice(index, price);
                System.out.println("Below is the list of stocks in the market after change: ");
                market.printMarket();
            }
            else if(action==4) {    //Track Client Profits
                
            }
            else if(action==5) {    //Notify Client
                
            }
            else if(action==6) {    //Approve New Client
                
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
