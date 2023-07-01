package com.example.demo.DAO;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO usertable (name,username,password,role)"
            + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM usertable where idusertable = ?";
    private final static String selectAllStatementString = "SELECT * FROM usertable";

    private final static String findByUsernameStatementString = "SELECT * FROM usertable where username = ?";

    public static User findById(int userId) {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1,userId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            toReturn = new User(userId, name, username,password, role);
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserDAO:findById " + e.getMessage());
        } finally {

            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static User findByUsername(String username) {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findByUsernameStatementString);
            findStatement.setString(1,username);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            //String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            toReturn = new User(name, username,password, role);
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<User> allUsers() {
        User toReturn = null;
        ArrayList<User> arr = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(selectAllStatementString);
            // findStatement.setLong(1,clientId);
            rs = findStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("idusertable");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                toReturn = new User(id,name, username,password, role);
                arr.add(toReturn);
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"UserDAO:getAllUsers  " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return arr;
    }

    public static int edit(int userId, String newName, String newUsername, String newPassword, String newRole) {
        String updateStatementString = "UPDATE usertable SET name = '" + newName + "', username = '" + newUsername + "', password = '" + newPassword + "', role = '" + newRole + "' where idusertable = " + userId;
        int toReturn = 0;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            int rs = updateStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

    public static int delete(int userId) {
        String deleteStatementString = "DELETE FROM usertable WHERE idusertable = " + userId;
        int toReturn = 0;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            int rs = deleteStatement.executeUpdate();

            toReturn = 1;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }

        return toReturn;
    }

    public static int insert(User user){
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try{
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1,user.getName());
            insertStatement.setString(2,user.getUsername());
            insertStatement.setString(3,user.getPassword());
            insertStatement.setString(4,user.getRole());
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
