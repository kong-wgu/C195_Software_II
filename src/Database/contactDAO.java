package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactDAO {

    /***
     * Gathers all contacts from the database, returns as a array list with all the contacts.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        String querySQL = "Select * from contacts";
        PreparedStatement ps =DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();

        // Gets all the contacts from the return, sets a new Contact from the returned query.
        try{
            while(rs.next()){
                long id = rs.getLong("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact newContact = new Contact(id, name, email);
                contactObservableList.add(newContact);
            }
            return contactObservableList;
        }catch (SQLException e){
            throw new SQLException("Could not load Customers.");
        }

    }

    /***
     * Gets the associated ID from the email of the contact.
     * @param email
     * @return
     * @throws SQLException
     */
    public static long getContactID(String email) throws SQLException{
        String querySQL = "Select * from contacts where Email = " + email;
        PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();
        long id = 0;
        try {
            // Should return the found contact, if not, will throw an error.
            while (rs.next()) {
                id = rs.getLong("Contact_ID");
            }
            return id;
        } catch (SQLException e){
            throw new SQLException("Error: Was not able to get User ID.");
        }

    }

}
