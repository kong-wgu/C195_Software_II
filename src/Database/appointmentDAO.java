package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class appointmentDAO {


    public static ObservableList<Appointment> getAllAppointments() throws SQLException{
        String querySQL = "Select * from appointments";
        PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

        try{
            while(rs.next()){
                long appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                String create_by = rs.getString("Created_By");
                long customerID = rs.getInt("Customer_ID");
                long userID = rs.getInt("User_ID");
                long contactID = rs.getInt("Contact_ID");

                Appointment app = new Appointment(appointmentID, title, description, location, type, start,
                        end, create_by, customerID, userID, contactID);
                appointmentObservableList.add(app);

            }

            return appointmentObservableList;

        } catch (SQLException e){
            throw new SQLException("Could not load All Appointments, Failed to retrieve current Database Connection.");
        }
    }

    public static boolean deleteAppointment(long id) throws SQLException{

        try{
            String querySQL = "Delete from appointments WHERE Appointment_ID = " + Long.toString(id);
            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(querySQL);
            ResultSet rs = ps.executeQuery();
            return true;

        }catch(SQLException e){
            throw new SQLException("Error: Could not delete appointment.");
        }

    }

}
