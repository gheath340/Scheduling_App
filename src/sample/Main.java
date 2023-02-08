package sample;

import DBAccess.DBCountries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginForm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();

        launch(args);
        //CODE GOES HERE
        //DBCountries.countryUpdate(7, "Canada");

        //long millis=System.currentTimeMillis();
        //java.sql.Date date=new java.sql.Date(millis);
        //DBCountries.countryInsert("Test", date, "Me", date, "Me");
        //DBCountries.countryDelete(7);
        //DBCountries.countriesGet();
        //DBCountries.countryGet("script");


        JDBC.closeConnection();
    }
}
