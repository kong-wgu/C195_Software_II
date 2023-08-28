package model;

public class Contact {

    public long ID;
    public String name;
    public String email;

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
