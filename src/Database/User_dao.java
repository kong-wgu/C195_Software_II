package Database;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User_dao {


    public static User validateUser(DBConnection conn, String username, String password) throws SQLException {

        try {
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND password = '" + password + "'";
            PreparedStatement validate = DBConnection.conn.prepareStatement(sqlQuery);
            var results = validate.executeQuery();
            if (results.next()) {
                long id = results.getLong("User_ID");
                return new User(id, username);
            }
            return null;
        }
        catch (SQLException e){
            throw new SQLException("Error: Cannot validate User.", e);
        }
    }


    public static boolean update_User(Connection conn, String username, String password) throws SQLException{

        try{
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND password = '" + password + "'";
            PreparedStatement validate = conn.prepareStatement(sqlQuery);
            var results = validate.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException e){
            throw new SQLException("Error: Cannot update User", e);
        }
    }

    public static ObservableList<User> getAllUsers() throws SQLException {

        String sqlQuery = "Select * from users";
        ObservableList<User> usersObservableList = FXCollections.observableArrayList();




    }

}
