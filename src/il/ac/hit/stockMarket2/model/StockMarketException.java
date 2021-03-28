package il.ac.hit.stockMarket2.model;

/**
 * StockMarketException class will be use to throw
 * a custom exception with a message
 */

public class StockMarketException extends Exception {

    /**
     * Class constructor.
     */
    public StockMarketException(String message) {
        super(message);
    }

    public StockMarketException(String message, Throwable cause) {
        super(message, cause);
    }
}
