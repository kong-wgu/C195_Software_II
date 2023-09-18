package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countryDAO {

    public static ObservableList<Country> getAllCountries() throws SQLException{
        String querySQL = "Select Country_ID, Country from countries";
        PreparedStatement ps  = DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();

        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                long id = rs.getLong("Country_ID");
                String name = rs.getString("Country");

                Country newCountry = new Country(id, name);
                countryObservableList.add(newCountry);
            }
            return countryObservableList;
        }catch (SQLException e){
            throw new SQLException("Error: Could not load Countries.");
        }
    }
}
