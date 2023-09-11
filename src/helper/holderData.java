package helper;

import model.Appointment;

public class holderData {

    private static long selected_Appointment;

    private static Appointment holderAppointment;

    public static void setSelected_Appointment(long num){selected_Appointment = num;}
    public static void setHolderAppointment(Appointment what){holderAppointment = what;}

    public static long getSelected_Appointment(){return selected_Appointment;}
    public static Appointment getHolderAppointment(){return holderAppointment;}

}
