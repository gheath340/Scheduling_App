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

    /**
     *
     * @return
     */
    public int getDivisionId(){ return this.divisionID; }
    /**
     *
     * @return
     */
    public String getDivision(){ return this.division; }
    /**
     *
     * @return
     */
    public Date getCreateDate(){ return this.createDate; }
    /**
     *
     * @return
     */
    public String getCreatedBy(){ return this.createdBy; }
    /**
     *
     * @return
     */
    public Timestamp getLastUpdate(){ return this.lastUpdate; }
    /**
     *
     * @return
     */
    public String getLastUpdatedBy(){ return this.lastUpdatedBy; }
    /**
     *
     * @return
     */
    public int getCountryID(){ return this.countryID; }

}
