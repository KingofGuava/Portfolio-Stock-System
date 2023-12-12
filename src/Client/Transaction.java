package Client;

import Manager.*;

public abstract class Transaction {
    protected StockInventory ownedStock;
    protected double balance;
    protected Market market;

    public Transaction(){}

    public void execute(){}
    public void placeOrder(Stock stock, double quantity){}
}
