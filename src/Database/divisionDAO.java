package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * This is a Data Access Object that works between the Division table and Division object.
 */
public class divisionDAO {

    /***
     * Gets all the division from the database and returns as a list.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Division> getAllDivisions() throws SQLException {

        String querySQL = "Select * from first_level_divisions";
        PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();

        try{
            while(rs.next()){
                long id = rs.getLong("Division_ID");
                String name = rs.getString("Division");
                long countryID = rs.getLong("Country_ID");
                Division newDivision = new Division(id, name, countryID);
                divisionObservableList.add(newDivision);
            }
            return divisionObservableList;
        }catch (SQLException e){
            throw new SQLException("Error: Could not get Divisions from Database.");
        }
    }
}
