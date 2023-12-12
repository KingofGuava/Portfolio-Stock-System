package Manager;

public interface StockObserver {
    void update(String symbol, double price);
}