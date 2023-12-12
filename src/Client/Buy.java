package Client;

import Manager.*;

import java.util.Scanner;

public class Buy extends Transaction {
    private Scanner sc;
    public Buy(){}

    @Override
    public void execute(){
        sc = new Scanner(System.in);
        market.printMarket();
        System.out.println("The balance you have now is: " + this.balance);
        System.out.println("Please input the index of the stock you would like to buy");
        int stockIdx = sc.nextInt();
        System.out.println("Please input how many quantity you would like to place the order");
        double quantity = sc.nextDouble();
        Stock targetStock = market.getMarket().get(stockIdx);
        placeOrder(targetStock, quantity);  //buy the selected stock with specified quantity
        System.out.println("You've bought the stock " + targetStock.getSymbol() + " for a quantity of " + quantity);
        System.out.println("The remaining balance you have now is: " + this.balance);
    }

    @Override
    public void placeOrder(Stock stock, double quantity){
        stock.setShare(quantity);
        this.ownedStock.getStockList().add(stock);                                  //add stock into owned stocks
        this.balance -= stock.getPrice() * quantity;                                //deduct money bc of purchase
    }

}
