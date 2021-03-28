package il.ac.hit.stockMarket2.viewmodel;

import il.ac.hit.stockMarket2.model.IModel;
import il.ac.hit.stockMarket2.model.Stock;
import il.ac.hit.stockMarket2.view.IView;

public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);
    public void addStock(Stock item);
    public void getStocks();

}
