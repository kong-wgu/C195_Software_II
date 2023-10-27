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

            if(isSucessful){
                fw.append("User: " + user + " has SUCCESSFULLY logged in at: " + datetime + "\n");
                fw.flush();
                fw.close();
                return true;
            }else{
                fw.append("User: " + user + " FAILED to log in at: " + datetime + "\n");
                fw.flush();
                fw.close();
            }
            return false;

        }catch(IOException e){
            throw new IOException("");
        }
    }

}
