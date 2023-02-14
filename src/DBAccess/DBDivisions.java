package DBAccess;

import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;

import java.sql.*;

public class DBDivisions {
    /**
     *
     * @param countryID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Division> getDivisions(int countryID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        while (rs.next()) {
            int divisionID= rs.getInt("Division_ID");
            String division= rs.getString("Division");
            Date createDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy= rs.getString("Last_Updated_By");
            int cID= rs.getInt("Country_ID");


            Division d = new Division(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, cID);
            divisions.add(d);
        }
        return divisions;
    }

    /**
     *
     * @param division
     * @return
     * @throws SQLException
     */
    public static int getDivisionID(String division) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        int divisionID = 0;
        
        while (rs.next()){
            divisionID = rs.getInt("Division_ID");
        }

        return divisionID;

    }

    public static String getDivision(int divisionID) throws SQLException{
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        String division = "";

        while (rs.next()){
            division = rs.getString("Division");
        }

        return division;
    }

    /**
     *
     * @param divisionID
     * @return
     * @throws SQLException
     */
    public static int getCountryID(int divisionID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        int countryID = 0;

        while (rs.next()){
            countryID = rs.getInt("Country_ID");
        }

        return countryID;

    }


}
