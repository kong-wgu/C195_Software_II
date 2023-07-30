package model;

public class Division {

    public long ID;
    public String name;
    public long countryID;

    public Division(long id, String name, long countryID){
        this.ID = id;
        this.name = name;
        this.countryID = countryID;
    }

    public long getID(){return ID;}


    public String getName(){return name;}


    public long getCountryID(){return countryID;}

}
