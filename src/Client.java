import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Account {
    //Attributes
    private double balance;
    private double unreaProfit;
    private double reaProfit;
    private StockInventory ownedStock;
    private ArrayList<Double> shareStock;
    private Scanner sc;

    //Constructor
    public Client(){
        this.balance = 0;
        this.unreaProfit = 0;
        this.reaProfit = 0;
        this.ownedStock = new StockInventory();
        this.shareStock = new ArrayList<Double>();
    }

    //Function
    public void Run(){
        new LogIn().Run();
        Market market = new Market();
        while(true){
            sc = new Scanner(System.in);
            System.out.println("Please decide your action by pressing a number from 1 to 5 ");
            int action = sc.nextInt();
            if(action==1) {         //Buy
                market.printMarket();
                System.out.println("The balance you have now is: " + this.balance);
                System.out.println("Please input the index of the stock you would like to buy");
                int stockIdx = sc.nextInt();
                System.out.println("Please input how many quantity you would like to place the order");
                double quantity = sc.nextDouble();
                Stock targetStock = market.getMarket().get(stockIdx);
                Buy(targetStock, quantity);  //buy the selected stock with specified quantity
                System.out.println("You've bought the stock " + targetStock.getSymbol() + " for a quantity of " + quantity);
                System.out.println("The remaining balance you have now is: " + this.balance);
            }
            else if(action==2) {    //Sell
                System.out.println("Please input the index of the stock you would like to buy");
                System.out.println("Please input how many quantity you would like to place the order");
            }
            else if(action==3) {    //Deposit
                System.out.println("Please input the amount of money you would like to deposit");
                double money = sc.nextDouble();
                Deposit(money);
            }
            else if(action==4) {    //Withdraw
                System.out.println("Please input the amount of money you would like to withdraw");
                double money = sc.nextDouble();
                Withdraw(money);
            }
            else if(action==5) {    //Check unrealized profit
                CheckUnreaProfit();
            }
            else if(action==6) {    //Check realized profit
                CheckReaProfit();
            }
            else if(action==7) {    //Display client's owned stock
                this.ownedStock.displayStock();
            }
        }
    }

    public void Buy(Stock stock, double quantity){
        this.ownedStock.getStockList().add(stock);                                  //add stock into owned stocks
        this.shareStock.add(quantity);
        this.balance -= stock.getPrice() * quantity;                                //deduct money bc of purchase
    }

    public void Sell(int stockIdx, double quantity){
        if(this.shareStock.get(stockIdx) > quantity){                               //if there are remaining shares after selling
            this.shareStock.set(stockIdx, this.shareStock.get(stockIdx)-quantity);  //set the corresponding share to the new value
        }
        else{                                                                       //client sells all of the shares he has
            this.ownedStock.getStockList().remove(stockIdx);                        //remove specified stock in owned stocks
            this.shareStock.remove(stockIdx);
        }
    }

    public void Deposit(double amount){
        System.out.println("Your current balance is: " + this.balance);
        System.out.println("How much money do you want to deposit?");
        this.balance += amount;                         //add money to wallet
        System.out.println("Your balance after deposit is: " + this.balance);
    }

    public void Withdraw(double amount){
        System.out.println("Your current balance is: " + this.balance);
        System.out.println("How much money do you want to withdraw?");
        this.balance -= amount;                         //deduct money from wallet
        System.out.println("Your balance after withdraw is: " + this.balance);
    }

    public void CheckUnreaProfit(){
        System.out.println("Your unrealized profit is: " + this.unreaProfit);
    }

    public void CheckReaProfit(){
        System.out.println("Your realized profit is: " + this.reaProfit);
    }
}
