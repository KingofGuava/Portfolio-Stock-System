import java.util.ArrayList;

public class StockInventory {
    //Attributes
    private ArrayList<Stock> stockList;

    //Constructor
    public StockInventory() {
        this.stockList = new ArrayList<Stock>();
    }

    //Access Func
    public ArrayList<Stock> getStockList(){
        return this.stockList;
    }

    //Func
    public void displayStock(){
        for(int i=0; i<stockList.size(); i++){
            System.out.println("[" + i + "] " + "Symbol: " + stockList.get(i).getSymbol() + " , Price: " + stockList.get(i).getPrice());
        }
    }
}
