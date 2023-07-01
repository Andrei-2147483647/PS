package com.example.demo.DAO;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.domain.Picture;
import com.example.demo.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PictureDAO {
    protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO picture (title, productId)"
            + " VALUES (?,?)";
    private final static String findStatementString = "SELECT * FROM picture where idpicture = ?";
    private final static String selectAllStatementString = "SELECT * FROM picture";

    private final static String findAllPicturesForProduct = "SELECT p.id" +
            "FROM picture p" +
            "JOIN product pr ON p.idproduct = pr.idproduct" +
            "WHERE p.idproduct = ?";

    public static Picture findById(int pictureId) {
        Picture toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1,pictureId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int prId = rs.getInt("idproduct");
            toReturn = new Picture(name, prId);
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"PictureDAO:findById " + e.getMessage());
        } finally {

            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Picture> allPictures() {
        Picture toReturn = null;
        ArrayList<Picture> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(selectAllStatementString);
            // findStatement.setLong(1,clientId);
            rs = findStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("title");
                int prId = rs.getInt("idproduct");
                toReturn = new Picture(id,name,prId);
                arr.add(toReturn);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"PictureDAO:getAllPictures  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }

    public static ArrayList<Picture> getProductPictures(int id) {
        Picture toReturn = null;
        ArrayList<Picture> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findAllPicturesForProduct);
            findStatement.setInt(1,id);
            rs = findStatement.executeQuery();

            while(rs.next()){
                int pid = rs.getInt("id");
                String name = rs.getString("title");
                int prId = rs.getInt("idproduct");
                toReturn = new Picture(pid,name,prId);
                arr.add(toReturn);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"PictureDAO:getAllPictures  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }

    public static int delete(int pictureId) {
        String deleteStatementString = "DELETE FROM picture WHERE id = " + pictureId;
        int toReturn = 0;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            int rs = deleteStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"PictureDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

    public static int insert(Picture picture){
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try{
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1,picture.getTitle());
            insertStatement.setString(2, String.valueOf(picture.getProductId()));

            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

}
