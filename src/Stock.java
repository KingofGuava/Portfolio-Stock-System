public class Stock {
    //Attributes
    private String symbol;
    private double price;

    //Constructor
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    //Access Function
    public String getSymbol(){
        return this.symbol;
    }
    public double getPrice(){
        return this.price;
    }

    //Mutator Function
    public void setPrice(double price){
        this.price = price;
    }
}
