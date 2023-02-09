package DBAccess;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBContacts {
    public static ObservableList<Contact> contactGet() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(id, name, email);
            contacts.add(contact);

        }
        return contacts;
    }

    public static ResultSet appointmentGet(int appID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String title = rs.getString("Title");
        }
        return rs;
    }

    public static int appointmentAdd(String title, String description, String location, String type, Date createDate,
                                     String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Date start, Date end,
                                     int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO appointments Appointment_ID = Default, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?," +
                "Last_Updated_By = ?, Create_date = ?, Created_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setDate(5,start);
        ps.setDate(6, end);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setDate(9, createDate);
        ps.setString(10, createdBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }
}
