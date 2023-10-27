package helper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/***
 * This class is to help record any logins that are sucessull.
 */
public class LoginHelper {
    private static String file = "login_activity.txt";

    /***
     * Determines if the user has logged in sucessfully, will return a true/false value back.
     * @param user
     * @param isSucessful
     * @return
     * @throws IOException
     */
    public static boolean addLogin(String user, boolean isSucessful) throws IOException {
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
