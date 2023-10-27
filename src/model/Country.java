package model;

/***
 * This class is hold on the same attributes of the Country table from the databse.
 * Contains Setters and Getter methods.
 */
public class Country {

    private long id;
    private String name;

    /***
     * Creates a Country object with the given information
     * @param id
     * @param name
     */
    public Country(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getID(){return id;}

    public String getName(){return name;}


}
