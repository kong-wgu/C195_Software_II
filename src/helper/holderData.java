package helper;

import model.Appointment;
import model.Customer;

public class holderData {

    private static long selected_Appointment;
    private static long selected_Customer;

    private static Appointment holderAppointment;
    private static Customer holderCustomer;

    public static void setSelected_Appointment(long num){selected_Appointment = num;}
    public static void setHolderAppointment(Appointment what){holderAppointment = what;}

    public static long getSelected_Appointment(){return selected_Appointment;}
    public static Appointment getHolderAppointment(){return holderAppointment;}

    public static void setSelected_Customer(long num){selected_Customer = num;}
    public static void setHolderCustomer(Customer what){holderCustomer = what;}

    public static long getSelected_Customer(){return selected_Customer;}
    public static Customer getHolderCustomer(){return holderCustomer;}

}
