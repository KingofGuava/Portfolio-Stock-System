import java.util.ArrayList;

public class Market {
    //Attributes
    private ArrayList<Stock> stockMarket;

    //Constructor
    public Market(){
        this.stockMarket = new ArrayList<Stock>();
        setMarket();
    }

    //Access Func
    public ArrayList<Stock> getMarket(){
        return this.stockMarket;
    }

    //Mutate Func
    public void setMarket(){
        this.stockMarket.add(new Stock("TSLA", 235.58));
        this.stockMarket.add(new Stock("UBER", 58.63));
        this.stockMarket.add(new Stock("NVDA", 445.10));
        this.stockMarket.add(new Stock("PFE", 29.28));
        this.stockMarket.add(new Stock("AMZN", 144.88));
        this.stockMarket.add(new Stock("APPL", 189.43));
        this.stockMarket.add(new Stock("AMD", 118.57));
        this.stockMarket.add(new Stock("RIOT", 15.00));
    }

    //Func
    public void printMarket(){
        for(int i=0; i<stockMarket.size(); i++){
            System.out.println("[" + i + "] " + "Symbol: " + stockMarket.get(i).getSymbol() + " , Price: " + stockMarket.get(i).getPrice());
        }
    }

    //Add a stock to the market
    public void addStock(Stock stock) {
        this.stockMarket.add(stock);
    }

    //Remove a stock from the market
    public void removeStock(int index) {
        this.stockMarket.remove(index);
    }

    //Modify price of a stock
    public void modifyStockPrice(int index, double price) {
        this.stockMarket.get(index).setPrice(price);
    }

}
