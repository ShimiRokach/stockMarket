package il.ac.hit.stockMarket2.model;

import java.sql.*;

public class Stock {

    private int id;
    private String name;
    private int amount;
    private double price;

    public Stock(int id, String name, int amount, double price) throws StockMarketException {
        setId(id);
        setName(name);
        setAmount(amount);
        setPrice(price);
    }

    public Stock(String name, int amount, double price) throws StockMarketException {
        setId(getLastId());
        setName(name);
        setAmount(amount);
        setPrice(price);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    /**
     * getLastId method will return the ID of the last item inserted by the user
     */
    public int getLastId() throws StockMarketException {

        //lastId will contain the ID of the last item inserted by the user
        int lastId = 0;
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(DerbyDBModel.protocol);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery("SELECT ID FROM stocks");
            if (!(rs.first())) {
                lastId = 0;
            } else {
                rs.last();
                lastId = rs.getInt("ID");
            }

        } catch (SQLException e) {
            throw new StockMarketException("Error Loading DB", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {

                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (rs != null) {

                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return lastId + 1;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }


}
