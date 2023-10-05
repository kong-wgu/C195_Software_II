package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.time.LocalDateTime;
import java.time.Month;

public class AppointmentReports {

    private String month;
    private String type;
    private long totalCount;

    private String divisionName;
    private long totaldivisionCustomers;


    public static ObservableList<AppointmentReports> getAppointmentReports(ObservableList<Appointment> appointmentList) throws NullPointerException{
        ObservableList<AppointmentReports> appointmentReportsList = FXCollections.observableArrayList();
        try{
            for(Appointment appointment: appointmentList){
                Month month = appointment.getStartTime().getMonth();
                String monthName = month.name();
                String type = appointment.getType();
                boolean found = false;
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


    public static ObservableList<AppointmentReports> getDivisionReports(ObservableList<Customer> customerList) throws NullPointerException{
        ObservableList<AppointmentReports> appointmentReportsList = FXCollections.observableArrayList();

        try{
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

                if(!found){
                    AppointmentReports app = new AppointmentReports("", "", 0, divisionName, 1);
                    appointmentReportsList.add(app);
                }
            }

            return appointmentReportsList;

        }catch(NullPointerException e){
            throw new NullPointerException("THere is a Null Customer within the List.");
        }

    }


    public AppointmentReports(String month, String type, long count, String divisionName, long divisionCount){
        this.month = month;
        this.type = type;
        this.totalCount = count;
        if(divisionName != null){this.divisionName = divisionName;}
        this.totaldivisionCustomers = divisionCount;
    }

    public void updateCount(long num){this.totalCount = this.totalCount + num;}

    public void updateDivisionCount(){this.totaldivisionCustomers += 1;}


    public String getDivisionName(){return this.divisionName;}
    public String getMonth(){return this.month;}
    public String getType(){return this.type;}
    public long getTotalCount(){return this.totalCount;}

}
