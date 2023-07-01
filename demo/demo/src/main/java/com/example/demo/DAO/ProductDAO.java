package com.example.demo.DAO;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name,price,stock)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM product where idproduct = ?";
    private final static String selectAllStatementString = "SELECT * FROM product";

    public static Product findById(int productId) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1,productId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            double price = Double.parseDouble(rs.getString("price"));
            int stock = Integer.parseInt(rs.getString("stock"));
            toReturn = new Product(productId, name, price, stock);
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"PorductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Product> allProducts() {
        Product toReturn = null;
        ArrayList<Product> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(selectAllStatementString);
            // findStatement.setLong(1,clientId);
            rs = findStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("idproduct");
                String name = rs.getString("name");
                double price = Double.parseDouble(rs.getString("price"));
                int stock = Integer.parseInt(rs.getString("stock"));
                toReturn = new Product(id,name, price, stock);
                arr.add(toReturn);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ProductDAO:getAllUsers  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }

    public static int edit(int productId, String newName, double newPrice, int newStock) {
        String updateStatementString = "UPDATE product SET name = '" + newName + "', price = '" + newPrice + "', stock = '" + newStock + "' where idproduct = " + productId;
        int toReturn = 0;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            int rs = updateStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"PrDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

    public static int delete(int productId) {
        String deleteStatementString = "DELETE FROM product WHERE idproduct = " + productId;
        int toReturn = 0;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            int rs = deleteStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"PrDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

    public static int insert(Product product){
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try{
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1,product.getName());
            insertStatement.setString(2,String.valueOf(product.getPrice()));
            insertStatement.setString(3,String.valueOf(product.getStock()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,"PrDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }


}
