package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class Appointment {
    private String title;
    private int appointmentID;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Timestamp createDate;
    private String createdBy;
    private int customerID;
    private int userID;
    private int contactID;


    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start,
                    Timestamp end, Timestamp lastUpdate, String lastUpdatedBy, Timestamp createDate, String createdBy, int customerID, int userID, int contactID){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getAppointmentID(){ return this.appointmentID; }

    public String getTitle(){ return this.title; }

    public String getDescription(){ return this.description; }

    public String getLocation(){ return this.location; }

    public String getType(){ return this.type; }

    public Timestamp getStart(){ return this.start; }

    public LocalDateTime getConvertedStart() {
        LocalDateTime time = getStart().toInstant().atZone(ZoneId.of("GMT")).toLocalDateTime();
        return time;
    }

    public Timestamp getEnd(){ return this.end; }

    public LocalDateTime getConvertedEnd() {
        LocalDateTime time = getEnd().toInstant().atZone(ZoneId.of("GMT")).toLocalDateTime();
        return time;
    }

    public Timestamp getCreateDate(){ return this.createDate; }

    public String getCreatedBy(){ return this.createdBy; }

    public Timestamp getLastUpdate(){ return this.lastUpdate; }

    public String getLastUpdatedBy(){ return this.lastUpdatedBy; }

    public int getCustomerID(){ return this.customerID; }

    public int getUserID(){ return this.userID; }

    public int getContactID(){ return this.contactID; }

}

