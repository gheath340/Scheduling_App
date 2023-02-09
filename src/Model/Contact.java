package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Contact {
    private String name;
    private int id;
    private String email;


    public Contact(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getContactID(){ return this.id; }

    public String getName(){ return this.name; }

    public String getEmail(){ return this.email; }

}
