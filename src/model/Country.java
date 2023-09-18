package model;

public class Country {

    private long id;
    private String name;

    public Country(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getID(){return id;}

    public String getName(){return name;}


}
