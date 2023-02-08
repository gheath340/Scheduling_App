package DBAccess;

import Model.Countries;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBCountries {

    public static void countriesGet() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            System.out.print(countryID + " | ");
            System.out.println(countryName);
        }
    }

    public static void countryGet(String updatedBy) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Last_Updated_By = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, updatedBy);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Date createDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Date lastUpdate = rs.getDate("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            System.out.print(countryID + " | ");
            System.out.print(countryName + " | ");
            System.out.print(createDate + " | ");
            System.out.print(createdBy + " | ");
            System.out.print(lastUpdate + " | ");
            System.out.println(lastUpdatedBy);
        }
    }

    public static int countryInsert(String Country, Date CreateDate, String CreatedBy, Date LastUpdate, String LastUpdatedBy) throws SQLException {
        String sql = "INSERT INTO countries (Country,Create_Date,Created_By,Last_Update,Last_Updated_By) VALUES (?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Country);
        ps.setDate(2, CreateDate);
        ps.setString(3, CreatedBy);
        ps.setDate(4, LastUpdate);
        ps.setString(5, LastUpdatedBy);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0){
            System.out.println("Insert Successful");
        }else{
            System.out.println("Insert failed");
        }
        return rowsAffected;
    }

    public static int countryUpdate(int countryID, String countryName) throws SQLException {
        String sql = "UPDATE countries SET Country = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, countryName);
        ps.setInt(2, countryID);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0){
            System.out.println("Update Successful");
        }else{
            System.out.println("Update failed");
        }
        return rowsAffected;
    }

    public static int countryDelete(int countryId) throws SQLException {
        String sql = "DELETE FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0){
            System.out.println("Delete Successful");
        }else{
            System.out.println("Delete failed");
        }
        return rowsAffected;

    }

    public static String getCountry(int countryID) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        String country = "";

        while (rs.next()) {
            country = rs.getString("Country");
        }
        return country;
    }



}
