package model;

import javax.management.Descriptor;
import java.time.LocalDateTime;

/***
 * This Class is the Appointment class where it will containing all methods regarding the setter and getters.
 * It will also be the ability to create an Appointment object as well.
 */
public class Appointment {

    private long ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private long customerID;
    private long userID;
    private long contactID;
    private String contactName;

    /***
     * Creates an appointment object with the given pass through attributes.
     * @param appointment_ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startTime
     * @param endTime
     * @param createdBy
     * @param customerID
     * @param userID
     * @param contactID
     */
     public Appointment(long appointment_ID,String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime,
                        String createdBy,long customerID, long userID, long contactID) {
         this.ID = appointment_ID;
         this.title = title;
         this.description = description;
         this.location = location;
         this.type = type;
         this.startTime = startTime;
         this.endTime = endTime;
         this.createdBy = createdBy;
         this.customerID = customerID;
         this.userID = userID;
         this.contactID = contactID;

     }


    public long getID() {return ID;}
    public void setID(long ID){
         this.ID = ID;
    }

    public String getTitle() {return title;}
    public void setTitle(String title){
         this.title = title;
    }

    public String getDescription() {return description;}
    public void setDescription(String description){
         this.description = description;
    }

    public String getLocation() {return location;}
    public void setLocation(String location){
         this.location = location;
    }

    public String getType(){return type;}
    public void setType(String type){
         this.location = location;
    }

    public LocalDateTime getStartTime() {return startTime;}
    public void setStartTime(LocalDateTime startTime){
         this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {return endTime;}
    public void setEndTime(LocalDateTime endTime){this.endTime = endTime;}

    public String getCreatedBy() {return createdBy;}
    public void setCreatedBy(String createdBy){
         this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {return lastUpdate;}
    public void setLastUpdate(LocalDateTime lastUpdate){
         this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {return lastUpdatedBy;}
    public void setLastUpdatedBy(String lastUpdatedBy){
         this.lastUpdatedBy = lastUpdatedBy;
    }

    public long getCustomerID() {return customerID;}
    public void setCustomerID(long customerID){
         this.customerID = customerID;
    }

    public long getUserID() {return userID;}
    public void setUserID(long userID){
         this.userID = userID;
    }


    public long getContactID() {return contactID;}
    public void setContactID(long contactID){
         this.contactID = contactID;
    }


    public String getContactName() {return contactName;}
    public void setContactName(String contactName) {
         this.contactName = contactName;
    }


}
