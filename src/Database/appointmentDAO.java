package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            ps.executeUpdate();
            return true;

        }catch(SQLException e){
            throw new SQLException("Error: Could not delete appointment.");
        }

    }

    public static void addAppointment(String title, String description, String location,
                                 String type, String startTime, String endTime, String CreateDate,
                                 String lastUpdate, String customerID, String userID, String contactID) throws SQLException{

        String loggedUser = User.userLoggedIn;
        String user = User.userLoggedIn;
        try{
            String insert = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start," +
                    " End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(insert);


            ps.setString(1 , title);
            ps.setString(2, description);
            ps.setString(3 , location);
            ps.setString(4 , type);
            ps.setTimestamp(5 , Timestamp.valueOf(startTime));
            ps.setTimestamp(6 , Timestamp.valueOf(endTime));
            ps.setTimestamp(7 ,Timestamp.valueOf(CreateDate));
            ps.setString(8 , user);
            ps.setTimestamp(9 ,Timestamp.valueOf(lastUpdate));
            ps.setString(10 , loggedUser);
            ps.setString(11 , customerID);
            ps.setString(12 , userID);
            ps.setString(13 , contactID);

            ps.executeUpdate();



        }catch(SQLException e){
            throw new SQLException("Error Uploading Appointment to DataBase");
        }

    }

    public static void modifyAppointment(String id, String title, String description, String location,
                                         String type, String startTime, String endTime,
                                         String lastUpdate, String customerID, String userID, String contactID) throws SQLException{

        String loggedUser = User.userLoggedIn;
        String user = User.userLoggedIn;
        try{
            String insert = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?," +
                    "Start=?, End=?, Last_Updated_By=?, Last_Update=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?;";

            PreparedStatement ps = DBConnection.getCurrentConnection().prepareStatement(insert);


            ps.setString(1 , title);
            ps.setString(2, description);
            ps.setString(3 , location);
            ps.setString(4 , type);
            ps.setTimestamp(5 , Timestamp.valueOf(startTime));
            ps.setTimestamp(6 , Timestamp.valueOf(endTime));
            ps.setString(7 , loggedUser);
            ps.setTimestamp(8 ,Timestamp.valueOf(lastUpdate));
            ps.setString(9 , customerID);
            ps.setString(10 , userID);
            ps.setString(11 , contactID);
            ps.setString(12, id);

            ps.executeUpdate();



        }catch(SQLException e){
            throw new SQLException("Error Uploading Appointment to DataBase");
        }

    }




}
