package DBAccess;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBCustomers {
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> customersGet() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostal = rs.getString("Postal_code");
            String customerPhone = rs.getString("Phone");
            Date createDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerDivision = rs.getInt("Division_ID");

            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal,
                    customerPhone, createDate, createdBy, lastUpdate, lastUpdatedBy, customerDivision);
            customers.add(customer);

        }
        return customers;
    }

    /**
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static ResultSet customerGet(int customerID) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPhone = rs.getString("Phone");
            String customerPostal = rs.getString("Postal_code");
            int customerDivision = rs.getInt("Division_ID");
        }
        return rs;
    }

    /**
     *
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int customerAdd(String name, String address, String postalCode, String phone, Date createDate,
                                   String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_date, Created_By, Last_Update, " +
                "Last_Updated_By, Division_ID) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setDate(5,createDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /**
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static int customerDelete(int customerID) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    /**
     *
     * @param customerID
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int updateCustomer(int customerID, String name, String address, String postal, String phone, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
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
