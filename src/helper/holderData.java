package helper;

import Database.customerDAO;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.SQLException;

/***
 * This class is a helper class that helps hold all data that the user selected from the mainscreen, this will help with transitioning over data.
 */
public class holderData {

    private static long selected_Appointment;
    private static long selected_Customer;

    private static Appointment holderAppointment;
    private static Customer holderCustomer;

    // Sets the selected Appointment to the pass through variable
    public static void setSelected_Appointment(long num){selected_Appointment = num;}

    // Sets the holding appointment to the pass through appointment placeholder
    public static void setHolderAppointment(Appointment what){holderAppointment = what;}

    public static long getSelected_Appointment(){return selected_Appointment;}
    public static Appointment getHolderAppointment(){return holderAppointment;}

    /***
     * sets the customer by going through all the customer within the list.
     * @param num
     * @throws SQLException
     */
    public static void setSelected_Customer(long num) throws SQLException {

        ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();

        for(Customer customer : allCustomers){
            if(customer.getID() == num){
                holderCustomer = customer;
                selected_Customer = num;
            }
        }

    }

    /***
     * Sets the holder customer object to the pass through customer object.
     * @param what
     */
    public static void setHolderCustomer(Customer what){holderCustomer = what;}

    public static long getSelected_Customer(){return selected_Customer;}
    public static Customer getHolderCustomer(){return holderCustomer;}

}
