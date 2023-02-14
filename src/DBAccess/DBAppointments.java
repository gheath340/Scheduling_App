package DBAccess;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBAppointments {
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> appointmentsGet() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        while (rs.next()) {
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(appID, title, description, location, type, start, end, lastUpdate,
                    lastUpdatedBy, createDate, createdBy, customerID, userID, contactID);
            appointments.add(appointment);

        }
        return appointments;
    }

    /**
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> appointmentsByCustomerID(int customerID) throws SQLException{
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        while (rs.next()) {
            int appID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int cID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(appID, title, description, location, type, start, end, lastUpdate,
                    lastUpdatedBy, createDate, createdBy, cID, userID, contactID);
            appointments.add(appointment);

        }
        return appointments;
    }

    /**
     *
     * @param appID
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @param title
     * @param description
     * @param location
     * @param type
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void appointmentAdd(String title, String description, String location, String type, Timestamp createDate,
                                      String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Timestamp start, Timestamp end,
                                      int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                     "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5,start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);

        ps.executeUpdate();

    }

    /**
     *
     * @param appID
     * @throws SQLException
     */
    public static void appointmentDelete(int appID) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appID);
        ps.executeUpdate();

    }

    /**
     *
     * @param appID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void updateAppointment(int appID, String title, String description, String location, String type, Timestamp lastUpdate,
                                         String lastUpdatedBy, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, " +
                "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, appID);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0){
            System.out.println("Update Successful");
        }else{
            System.out.println("Update failed");
        }
    }
}
