package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class customerDAO {

    /***
     * Returns a list of all customers from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException{

        // Must get all the divisions so we can set the division name when making a Customer object.
        ObservableList<Division> divisionObservableList = divisionDAO.getAllDivisions();
        String divisionName = "";

        String querySQL = "Select * from customers";
        PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        try{
            while(rs.next()){
                long ID = rs.getLong("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                long divisionID = rs.getLong("Division_ID");
                String createdBy = rs.getString("Created_By");

                // Searches for the name of the Division rather than the ID.
                for(Division division: divisionObservableList){
                    if(divisionID == division.getID()){
                        divisionName = division.getName();
                    }
                }

                Customer customer = new Customer(ID, name, address, postalCode, phone, divisionID, divisionName);
                customerObservableList.add(customer);

            }
            return customerObservableList;

        }catch (SQLException e){
            throw new SQLException("Could not Load Customers from Database!");
        }

    }

    /***
     * Adds a customer into the database, ID will always auto add
     * @param newCustomer
     * @param createDate
     * @throws SQLException
     */
    public static void addCustomer(Customer newCustomer, String createDate) throws SQLException {

        String user = User.userLoggedIn;
        String name = newCustomer.getName();
        String address = newCustomer.getAddress();
        String postalCode = newCustomer.getPostalCode();
        String phone = newCustomer.getPhone();
        String divisionID = String.valueOf(newCustomer.getDivisionID());

        try {
            String insert = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, " +
                    "Create_Date, Created_by, Last_Update, Last_Updated_By, Division_ID) Values(" +
                    "?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(insert);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, createDate);
            ps.setString(6, user);
            ps.setString(7, createDate);
            ps.setString(8, user);
            ps.setString(9, divisionID);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error Adding Customer to the database");
        }
    }


    /***
     * Updates customer by using a given Customer object and the division name to update the database
     * @param newCustomer
     * @param lastUpdate
     * @param division
     * @return
     * @throws SQLException
     */
    public static boolean updateCustomer(Customer newCustomer, String lastUpdate, String division) throws SQLException{
        long Customer_ID = newCustomer.getID();

        // Sets all variable placeholder to holder attributes of the customer object.
        String name = newCustomer.getName();
        String address = newCustomer.getAddress();
        String postalCode = newCustomer.getPostalCode();
        String phone = newCustomer.getPhone();


        String divisionID = division;

        // Sets the user who is currently logged on.
        String lastUpdatedBy = User.userLoggedIn;

        try{
            String query = "UPDATE customers SET Customer_Name=? , Address=?, Postal_Code=?, Phone=?" +
                    ", Last_Update=?, Last_Updated_By=?, Division_ID=? Where Customer_ID=?";

            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(lastUpdate));
            ps.setString(6, lastUpdatedBy);
            ps.setString(7, divisionID);
            ps.setString(8, Long.toString(Customer_ID));

            ps.executeUpdate();

        }catch(SQLException e){
            throw new SQLException("Could not update Customer, please check your query or data objects.");
        }

        return true;

    }

    /***
     * Deletes the desired row by using the customer ID.
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static boolean deleteCustomer(long customerID) throws SQLException{

        try{
            String querySQL = "Delete from customers WHERE Customer_ID = " + Long.toString(customerID);
            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            throw new SQLException("Error deleting customer, please check database connection");
        }
    }

 }
