package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Division {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public Division(int divisionID, String division, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID){
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    public int getDivisionId(){ return this.divisionID; }

    public String getDivision(){ return this.division; }

    public Date getCreateDate(){ return this.createDate; }

    public String getCreatedBy(){ return this.createdBy; }

    public Timestamp getLastUpdate(){ return this.lastUpdate; }

    public String getLastUpdatedBy(){ return this.lastUpdatedBy; }

    public int getCountryID(){ return this.countryID; }

}
