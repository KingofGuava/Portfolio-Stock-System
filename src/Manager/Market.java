package Manager;

import Client.*;
import java.util.ArrayList;

public class Market implements MarketWatchList {
    //Attributes
    private static ArrayList<Stock> stockMarket = new ArrayList<Stock>();
    private ArrayList<Client> observers = new ArrayList<Client>();

    static {
        stockMarket.add(new Stock("TSLA", 235.58, 10000, 235.58));
        stockMarket.add(new Stock("UBER", 58.63, 10000, 58.63));
        stockMarket.add(new Stock("NVDA", 445.10, 10000, 445.10));
        stockMarket.add(new Stock("PFE", 29.28, 10000, 29.28));
        stockMarket.add(new Stock("AMZN", 144.88, 10000, 144.88));
        stockMarket.add(new Stock("APPL", 189.43, 10000, 189.43));
        stockMarket.add(new Stock("AMD", 118.57, 10000, 118.57));
        stockMarket.add(new Stock("RIOT", 15.00, 10000, 15.00));
    }

    

    //Access Func
    public ArrayList<Stock> getMarket(){
        return stockMarket;
    }
    
    //Func
    public void printMarket(){
        for(int i=0; i<stockMarket.size(); i++){
            System.out.println("[" + i + "] " + "Symbol: " + stockMarket.get(i).getSymbol() + " , Price: " + stockMarket.get(i).getPrice());
        }
    }

    //Add a stock to the market
    public void addStock(Stock stock) {
        stockMarket.add(stock);
    }

    //Remove a stock from the market
    public void removeStock(int index) {
        stockMarket.remove(index);
    }

    //Modify price of a stock
    public void modifyStockPrice(int index, double price) {
        stockMarket.get(index).setPrice(price);
    }

    @Override
    public void registerObserver(Client observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Client observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String stockSymbol, double stockPrice) {
        for (Client observer : observers) {
            observer.update(stockSymbol, stockPrice);
        }
    }

}
