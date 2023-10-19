package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LoginHelper {
    private static String file = "login_activity.txt";

    public static void addLogin(String user, boolean isSucessful) throws IOException {
        try{

            String datetime = ZonedDateTime.now(ZoneOffset.UTC).toString();
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            if(isSucessful){
                pw.print("User:" + user + " has successfully logged in at: " + datetime + "\n");
                return true;
            }else{
                pw.print("User:" + user + " failed to log in at: " + datetime + "\n");
            }
            return false;

        }catch(IOException e){
            throw new IOException("");
        }
    }

}
