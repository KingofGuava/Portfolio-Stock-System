package Manager;

public class Stock {
    //Attributes
    private String symbol;
    private double price;
    private double share;
    private double buyInPrice;

    //Constructor
    public Stock(String symbol, double price, double share, double buyInPrice) {
        this.symbol = symbol;
        this.price = price;
        this.share = share;
        this.buyInPrice = buyInPrice;
    }

    //Access Function
    public String getSymbol(){
        return this.symbol;
    }
    public double getPrice(){
        return this.price;
    }
    public double getShare(){
        return this.share;
    }
    public double getBuyInPrice(){
        return this.buyInPrice;
    }

    //Mutator Function
    public void setPrice(double price){
        this.price = price;
    }
    public void setShare(double share){
        this.share = share;
    }
}
