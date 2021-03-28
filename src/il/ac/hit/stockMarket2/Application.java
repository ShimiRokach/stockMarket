package il.ac.hit.stockMarket2;


import il.ac.hit.stockMarket2.model.DerbyDBModel;
import il.ac.hit.stockMarket2.model.IModel;
import il.ac.hit.stockMarket2.model.StockMarketException;
import il.ac.hit.stockMarket2.view.IView;
import il.ac.hit.stockMarket2.view.View;
import il.ac.hit.stockMarket2.viewmodel.IViewModel;
import il.ac.hit.stockMarket2.viewmodel.ViewModel;


public class Application {
    public static void main(String args[]) {

        //creating the application components
        IModel model = null;
        try {
            model = new DerbyDBModel();
        } catch (StockMarketException e) {
            e.printStackTrace();
        }
        IView view = new View();
        IViewModel vm = new ViewModel();

        //connecting the components with each other
        view.setViewModel(vm);
        vm.setModel(model);
        vm.setView(view);

    }
}
