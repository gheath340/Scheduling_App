package DBAccess;

import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBContacts {
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> getContacts() throws SQLException {
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

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public static int getContactID(String name) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int id = 0;

        while (rs.next()) {
            id = rs.getInt("Contact_ID");
        }
        return id;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getContactName(int id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String name = "";

        while (rs.next()) {
            name = rs.getString("Contact_Name");
        }
        return name;
    }
}
