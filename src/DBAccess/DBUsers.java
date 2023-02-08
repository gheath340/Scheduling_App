package DBAccess;

import sample.JDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

    public static String userGet(String userName, String password){
        String matches = "";
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String pass = rs.getString("Password");
                System.out.println(pass);
                System.out.println(password);
                if (pass.equals(password)) {
                    matches = "true";
                } else {
                    matches = "false";
                }
            }

        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return matches;
    }
}
