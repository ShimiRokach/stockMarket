package il.ac.hit.stockMarket2.view;

import il.ac.hit.stockMarket2.model.Stock;
import il.ac.hit.stockMarket2.viewmodel.IViewModel;

import java.util.List;

public interface IView {

    public void setViewModel(IViewModel vm);
    public void showMessage(String text);
    public void showItems(List<Stock> costItems);
    public void setItems(List<Stock> items);

}
