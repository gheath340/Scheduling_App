package Model;

public class AppointmentRecord {
    private String month;
    private String type;
    private String count;

    public AppointmentRecord(String month, String type, String count){
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     *
     * @return
     */
    public String getMonth(){ return this.month; }

    /**
     *
     * @return
     */
    public String getType(){ return this.type; }

    /**
     *
     * @return
     */
    public String getCount(){ return this.count; }

    /**
     *
     * @param count
     */
    public void setCount(String count){ this.count = count;}
}
