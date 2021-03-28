package il.ac.hit.stockMarket2.viewmodel;

import il.ac.hit.stockMarket2.model.IModel;
import il.ac.hit.stockMarket2.model.Stock;
import il.ac.hit.stockMarket2.model.StockMarketException;
import il.ac.hit.stockMarket2.view.IView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void addStock(Stock item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addStock(item);
                    view.showMessage("stock was added successfully");
                    List<Stock> items = model.getStocks();
                    view.showItems(items);
                } catch (StockMarketException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });

    }

    @Override
    public void getStocks() {

        try {
            //get the cost items list and pass it to view
            List<Stock> items = model.getStocks();
            //view.showItems(items);
            view.setItems(items);
        } catch (StockMarketException e) {
            view.showMessage("failed to set items list.." + e.getMessage());

        }
    }
}
