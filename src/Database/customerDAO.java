package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class customerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException{
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

    public static boolean updateCustomer(Customer newCustomer, LocalTime lastUpdate) throws SQLException{
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        customerObservableList = getAllCustomers();
        long newCustomer_ID = newCustomer.getID();

        for(Customer customer : customerObservableList){
            long customerID = customer.getID();

            if(newCustomer_ID == customerID){

                String name = newCustomer.getName();
                String address = newCustomer.getAddress();
                String postalCode = newCustomer.getPostalCode();
                String phone = newCustomer.getPhone();

                String divisionID = String.valueOf(newCustomer.getDivisionID());

                String lastUpdatedBy = User.userLoggedIn;

                try{
                    String query = "UPDATE customers SET Customer_Name=? , Address=?, Postal_Code=?, Phone=?" +
                            ", Last_Update=?, LastUpdated_By=?, Division_ID=?";

                    PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(query);

                    ps.setString(1, name);
                    ps.setString(2, address);
                    ps.setString(3, postalCode);
                    ps.setString(4, phone);
                    ps.setTimestamp(5, lastUpdate);
                    ps.setString(6, divisionID);


                }catch(SQLException e){
                    throw new SQLException("Could not update Customer, please check your query or data objects.");
                }

                return true;
            }
        }
        return false;
    }

 }
