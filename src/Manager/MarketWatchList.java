package Manager;

import Client.*;

public interface MarketWatchList {
    void registerObserver(Client observer);
    void removeObserver(Client observer);
    void notifyObservers(String stockSymbol, double stockPrice);
}
