package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

 }
