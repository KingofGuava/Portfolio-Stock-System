package Client;
import Manager.*;

import java.util.Scanner;
public class Sell extends Transaction {
    private Scanner sc;
    public Sell(){}

    @Override
    public void execute(){
        sc = new Scanner(System.in);
        System.out.println("Please input the index of the stock you would like to buy");
        int index = sc.nextInt();
        System.out.println("Please input how many quantity you would like to place the order");
        double quantity = sc.nextDouble();
        Stock stockToSell = this.ownedStock.getStockList().get(index);
        placeOrder(stockToSell, quantity);
    }

    @Override
    public void placeOrder(Stock stockToSell, double quantity){
        double ownedShare = stockToSell.getShare();
        if(ownedShare > quantity){                                                  //if there are remaining shares after selling
            stockToSell.setShare(ownedShare-quantity); //set the corresponding share to the new value
            this.balance += stockToSell.getPrice() * quantity;
        }
        else{                                                                       //client sells all of the shares he has
            /////////this.ownedStock.getStockList().remove(stockIdx);                        //remove specified stock in owned stocks
            this.balance += stockToSell.getPrice() * ownedShare;
        }
    }
}
