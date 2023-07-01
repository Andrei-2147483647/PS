package com.example.demo.DAO;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO ordertable (date, price)"
            + " VALUES (?,?)";
    private static final String insertOPStatementString = "INSERT INTO order_products (order_id, product_id, quantity)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM ordertable where id = ?";
    private final static String selectAllStatementString = "SELECT * FROM order";
    private final static String selectProductsFromOrder = "SELECT p.*" +
            " FROM product p" +
            " JOIN order_products op ON p.idproduct = op.product_id" +
            " WHERE op.order_id = ?";


    private static double computePrice(ArrayList<Product> products){
        double sum = 0;
        for(Product p : products) {
            sum += p.getPrice();
        }
        return sum;
    }

    public static Order findById(int orderId) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(OrderDAO.findStatementString);
            findStatement.setLong(1, orderId);
            rs = findStatement.executeQuery();
            rs.next();

            String data = rs.getString("date");

            ArrayList<Product> arr = findOrderById(orderId);
            double pr = OrderDAO.computePrice(arr);

            toReturn = new Order(orderId, data, pr);
        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(OrderDAO.insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, order.getDate());
            insertStatement.setString(2, String.valueOf(0.0));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public  static int insertOrderProduct(int orderId, int productId, int quantity) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(OrderDAO.insertOPStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, String.valueOf(orderId));
            insertStatement.setString(2, String.valueOf(productId));
            insertStatement.setString(3, String.valueOf(quantity));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:insert orderproduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static ArrayList<Product> findOrderById(int orderId) {
        Product toReturn = null;
        ArrayList<Product> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(OrderDAO.selectProductsFromOrder);
            findStatement.setLong(1, orderId);
            rs = findStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double price = Double.parseDouble(rs.getString("price"));
                int stock = Integer.parseInt(rs.getString("stock"));
                toReturn = new Product(name, price, stock);
                arr.add(toReturn);
            }
        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:getProductsFromOrder  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }

    public static int delete(int orderId) {
        String deleteStatementString = "DELETE FROM ordertable WHERE id = " + orderId;
        int toReturn = 0;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            int rs = deleteStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

   public static ArrayList<Order> allOrders() {
        Order toReturn = null;
        ArrayList<Order> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(OrderDAO.selectAllStatementString);
            // findStatement.setLong(1,clientId);
            rs = findStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                double price = Double.parseDouble(rs.getString("price"));

                toReturn = new Order(id, date, price);
                arr.add(toReturn);
            }
        } catch (SQLException e) {
            OrderDAO.LOGGER.log(Level.WARNING, "OrderDAO:getAllOrders  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }
}

