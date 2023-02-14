package Model;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private String name;
    private int id;
    private String address;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    public Customer(int id, String name, String address, String postalCode, String phone, Date createDate,
                    String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     *
     * @return
     */
    public int getId(){ return this.id; }

    /**
     *
     * @return
     */
    public String getName(){ return this.name; }
    /**
     *
     * @return
     */
    public String getAddress(){ return this.address; }
    /**
     *
     * @return
     */
    public String getPostalCode(){ return this.postalCode; }
    /**
     *
     * @return
     */
    public String getPhone(){ return this.phone; }
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
    public int getDivisionID(){ return this.divisionID; }

}
