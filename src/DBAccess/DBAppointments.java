package DBAccess;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBAppointments {

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
            Date createDate = rs.getDate("Create_Date");
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

    public static int appointmentDelete(int appID) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appID);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }
    // still need to change
    public static int updateAppointment(int customerID, String name, String address, String postal, String phone, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, lastUpdate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionID);
        ps.setInt(8, customerID);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0){
            System.out.println("Update Successful");
        }else{
            System.out.println("Update failed");
        }
        return rowsAffected;
    }
}