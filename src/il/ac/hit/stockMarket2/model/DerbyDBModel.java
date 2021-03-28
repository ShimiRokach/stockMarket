package il.ac.hit.stockMarket2.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DerbyDBModel implements IModel {

    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:StockMarketDB2;create=true";

    /**
     * Class constructor.
     */
    public DerbyDBModel() throws StockMarketException {
        try {
            initDB();
        } catch (StockMarketException e) {
            throw new StockMarketException("ERROR Loading the DataBase", e);
        }
    }

    /**
     * initDB method will create the table for stocks if it don't exist
     */
    @Override
    public void initDB() throws StockMarketException {

        //Connection connection = null;

        try {
            Class.forName(driver);
            createTableIfNotExist("stocks");

        } catch (ClassNotFoundException | StockMarketException e) {
            throw new StockMarketException("ERROR init the DB", e);
        }

    }

    /**
     * The createTable method create the stocks table in the DB. The table will be
     * create only if it's not exists
     *
     * @param tableName
     * @throws StockMarketException
     */
    @Override
    public void createTableIfNotExist(String tableName) throws StockMarketException {

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(protocol);

            boolean tableExists = false;

            //checking whether table already exists
            if (connection != null) {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);

                if (rs.next()) {
                    tableExists = true;
                }
            }

            if (!tableExists) {

                statement = connection.createStatement();

                int res = statement.executeUpdate("create table stocks ("
                        + "ID INT,"
                        + "Name VARCHAR(255),"
                        + "Amount INT,"
                        + "Price DOUBLE )");

                // check the return value of executeUpdate to validate that create table worked
                if (res < 0) {
                    throw new StockMarketException("create table stocks failed");
                }

            }

        } catch (SQLException e) {

            throw new StockMarketException("ERROR Loading the DataBase", e);
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
        }

    }

    /**
     * addStock method will add new input stock to stocks table
     */
    @Override
    public void addStock(Stock item) throws StockMarketException {
        Connection connection = null;
        PreparedStatement pst = null;
        try {

            connection = DriverManager.getConnection(protocol);

            String query = "INSERT INTO stocks VALUES (?,?,?,?)";
            pst = connection.prepareStatement(query);
            pst.setInt(1, item.getId());
            pst.setString(2, item.getName());
            pst.setInt(3, item.getAmount());
            pst.setDouble(4, item.getPrice());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new StockMarketException("Insert Data Failed", e);
        } finally {

            if (pst != null) {

                try {
                    pst.close();
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
        }
    }

    /**
     * getCostItems method will return a list of all the stocks in the stocks table
     */
    @Override
    public List<Stock> getStocks() throws StockMarketException {

        //costItems will contain the items return from cost items table
        List<Stock> stocks = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM stocks");
            while (rs.next()) {

                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int amount = rs.getInt("Amount");
                double price = rs.getDouble("Price");

                //create the CostItem and add it to the CostItem list
                Stock item = new Stock(id,name, amount, price);
                stocks.add(item);
            }

        } catch (SQLException e) {
            throw new StockMarketException("Failed to view cost table!", e);
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

        return stocks;

    }

}
