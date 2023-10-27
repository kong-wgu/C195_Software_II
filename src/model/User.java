package model;

import java.time.format.DateTimeFormatter;

/***
 * This class is hold all attributes of the user, specifically the user who is able ot log in sucessfully.
 */
public class User {
    public long id;
    public String name;

    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter.ofPattern("HH:mm");

    public static String userLoggedIn;

    /***
     * Creates a User Object with the given information.
     * @param id
     * @param name
     */
    public User(long id, String name){
        this.id  = id;
        this.name = name;
    }

    public String getUserName(){return name;}

    public long getUserID(){return id;}


}
