package model;

import java.time.LocalDateTime;

/***
 * This class is store all information that the customer table from database.
 * It contains getter and setter methods, including mehtod to allow create a object of this class to use.
 */
public class Customer {

    private long ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private long divisionID;
    private String divisionName;

    /***
     * Creates a Customer object with the given data.
     * @param ID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @param divisionName
     */
    public Customer(long ID,String name, String address, String postalCode, String phone, long divisionID, String divisionName){
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    public long getID() {return ID;}
    public void setID(long ID) {this.ID = ID;}

    public String getName(){return name;}
    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){return address;}
    public void setAddress(String address){
        this.address = address;
    }

    public String getPostalCode(){return postalCode;}
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getPhone(){return phone;}
    public void setPhone(String phone){
        this.phone = phone;
    }

    public long getDivisionID() { return divisionID;}
    public void setDivisionID(long id){
        this.ID = id;
    }

    public String getDivisionName(){return divisionName;}
    public void setDivisionName(String name){
        this.divisionName = name;
    }

}
