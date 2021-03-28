package il.ac.hit.stockMarket2.model;

public class simple {

    public static void main(String[] args){

        //creating the application components
        DerbyDBModel model;
        try {
            model = new DerbyDBModel();
            //model.addStock(new Stock(2,"www",100,3));
            System.out.println(model.getStocks());
        } catch (StockMarketException e) {
            e.printStackTrace();
        }

    }

}
