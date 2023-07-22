package model;

import java.time.LocalDateTime;

public class Customer {

    private long id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private long divisionID;

    public Customer(String name, String address, String postalCode, String phone, String createdBy, long divisionID){
         this.name = name;
         this.address = address;
         this.postalCode = postalCode;
         this.phone = phone;
         this.createdBy = createdBy;
         this.divisionID = divisionID;
    }


}
