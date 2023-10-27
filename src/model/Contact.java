package model;

/***
 * This class is to contain all data like the contact table from the database,
 * contains getters and setter methods, in
 */
public class Contact {

    public long ID;
    public String name;
    public String email;

    /***
     * Creates a Contract object with the given values that is pass through.
     * @param id
     * @param name
     * @param email
     */
    public Contact(long id, String name, String email){
        this.ID = id;
        this.name = name;
        this.email = email;

    }

    public String getName(){
        return this.name;
    }

    public Long getID(){
        return (this.ID);
    }

}
