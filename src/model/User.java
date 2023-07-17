package model;

import java.time.format.DateTimeFormatter;

/**
 * This is hte POJO for the user class containing
 * methods for storing data using the DAO Class.
 * @author Kong Chang
 */

public class User {
    public long id;
    public String name;

    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter.ofPattern("HH:mm");

    public User(long id, String name){
        this.id  = id;
        this.name = name;
    }
}
