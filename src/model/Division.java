package model;

/***
 * This Class is to in house all the characteristics of the Division from the database.
 * It contains get and set methods to access the private constructors.
 * Also includes a method on creating a object of itself to be used.
 */
public class Division {

    public long ID;
    public String name;
    public long countryID;

    /***
     * Creates a Division object with the given information.
     * @param id
     * @param name
     * @param countryID
     */
    public Division(long id, String name, long countryID){
        this.ID = id;
        this.name = name;
        this.countryID = countryID;
    }

    public long getID(){return ID;}


    public String getName(){return name;}


    public long getCountryID(){return countryID;}

}
