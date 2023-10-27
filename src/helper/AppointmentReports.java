package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.time.LocalDateTime;
import java.time.Month;

/***
 * This class is a helper class that will contain methods and lists to help with the report form.
 * Should be keeping or returning the list of the data
 */
public class AppointmentReports {

    private String month;
    private String type;
    private long totalCount;

    private String divisionName;
    private long totaldivisionCustomers;

    /***
     * Gathers all information on the appoinments through the pass thorugh Appointment list to be compiled as a report. Returns as a List containing AppointmentReports objects.
     * @param appointmentList
     * @return
     * @throws NullPointerException
     */
    public static ObservableList<AppointmentReports> getAppointmentReports(ObservableList<Appointment> appointmentList) throws NullPointerException{
        // Sets the list variable placeholder to collect the appointment Reports.
        ObservableList<AppointmentReports> appointmentReportsList = FXCollections.observableArrayList();
        try{
            // Runs through all appointments from the given List containing all appointments.
            for(Appointment appointment: appointmentList){
                Month month = appointment.getStartTime().getMonth();
                String monthName = month.name();
                String type = appointment.getType();
                boolean found = false;

                // checks to see if appointment exists within the same month. If not, will add on the list.
                for(AppointmentReports app : appointmentReportsList){
                    if(monthName.equals(app.getMonth())){
                        found = true;
                        if(type.equals(app.getType())){
                            app.updateCount(1);
                        }else{
                            AppointmentReports newApp = new AppointmentReports(monthName, type, 1, null, 0);
                            appointmentReportsList.add(newApp);
                            break;
                        }
                    }
                }

                // If the appointment does not exist within the AppointmentReports, we will add it to the list.
                if(!found){
                    AppointmentReports app = new AppointmentReports(monthName, type, 1,null, 0);
                    appointmentReportsList.add(app);
                }

            }

            return appointmentReportsList;

        }catch(NullPointerException e){
            throw new NullPointerException("There is a Null Appointment within the list.");
        }

    }

    /***
     * Gathers all the divisions from given pass through variable and compiles into a AppointmentReport List.
     * @param customerList
     * @return
     * @throws NullPointerException
     */
    public static ObservableList<AppointmentReports> getDivisionReports(ObservableList<Customer> customerList) throws NullPointerException{
        ObservableList<AppointmentReports> appointmentReportsList = FXCollections.observableArrayList();

        try{
            // Using all the customers from the given list, we would sort through and get division of each one.
            for(Customer customer: customerList){
                String divisionName = customer.getDivisionName();
                boolean found = false;
                for(AppointmentReports app: appointmentReportsList){
                    if(divisionName.equals(app.getDivisionName())){
                        found = true;
                        app.updateDivisionCount();;
                        break;
                    }
                }

                // If found, will create a new Appointment Report object and add onto the list
                if(!found){
                    AppointmentReports app = new AppointmentReports("", "", 0, divisionName, 1);
                    appointmentReportsList.add(app);
                }
            }

            // returns all the appointmentReport objects that within all the customers.
            return appointmentReportsList;

        }catch(NullPointerException e){
            throw new NullPointerException("THere is a Null Customer within the List.");
        }

    }

    /***
     * Creates a AppointmentReport object with the given data
     * @param month
     * @param type
     * @param count
     * @param divisionName
     * @param divisionCount
     */
    public AppointmentReports(String month, String type, long count, String divisionName, long divisionCount){
        this.month = month;
        this.type = type;
        this.totalCount = count;
        if(divisionName != null){this.divisionName = divisionName;}
        this.totaldivisionCustomers = divisionCount;
    }

    /** Updates the current count to the desired amount from the pass through */
    public void updateCount(long num){this.totalCount = this.totalCount + num;}

    /** Updates the division count by 1 */
    public void updateDivisionCount(){this.totaldivisionCustomers = this.totaldivisionCustomers + 1;}

    /** Returns the division name */
    public String getDivisionName(){return this.divisionName;}

    /** returns the given month */
    public String getMonth(){return this.month;}

    /** returns the type */
    public String getType(){return this.type;}


}
