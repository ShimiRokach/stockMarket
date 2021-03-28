package il.ac.hit.stockMarket2.model;

import java.util.List;

public interface IModel {

    void initDB() throws StockMarketException;
    void createTableIfNotExist(String tableName) throws StockMarketException;
    void addStock(Stock item) throws StockMarketException;
    List<Stock> getStocks() throws StockMarketException;
}
