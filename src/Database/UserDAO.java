package Database;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * This class is a data access object that is a layer between the database and the User object.
 */
public class UserDAO {

    /***
     * Validates user by the given username and password inputs, will return a User object back.
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static User validateUser(String username, String password) throws SQLException {

        try {
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + username + "' AND password = '" + password + "'";
            PreparedStatement validate = DBConnection.getCurrentConnection().prepareStatement(sqlQuery);
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

    /***
     * Gathers all user from the database and returns back as a list.
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws SQLException {

        String sqlQuery = "Select * from users";
        PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();

        ObservableList<User> usersObservableList = FXCollections.observableArrayList();

        try{
            while(rs.next()){
                long id = rs.getLong("User_ID");
                String name = rs.getString("User_Name");

                User newUser = new User(id, name);
                usersObservableList.add(newUser);
            }

            return usersObservableList;
        }catch (SQLException e){
            throw new SQLException("Error: Cannot get all users.");
        }
    }

}
