package sample;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBUsers;
import Model.Appointment;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    public TextField usernameField;
    public TextField passwordField;
    public Label loginError;
    public Button loginButton;
    public Label zoneLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label appointmentLabel;

    public static String user = "";
    public static String activityOutput = "";

    public LoginController() throws IOException {
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneLabel.setText(ZoneId.systemDefault().getId());

        ResourceBundle rb = ResourceBundle.getBundle("sample/Nat", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println("Its in french");
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
        }else {
            System.out.println("Its in english");
        }

    }

    /**
     *
     * @return
     */
    public static String getUser(){ return user; }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void loginOnClick(ActionEvent actionEvent) throws SQLException, IOException {
        ResourceBundle rb = ResourceBundle.getBundle("sample/Nat", Locale.getDefault());

        String userName = usernameField.getText();
        String password = passwordField.getText();

        String matches = DBUsers.userGet(userName, password);
        if (matches.equals("true")){
            long millis = System.currentTimeMillis();
            Timestamp timeStamp = new Timestamp(millis);
            activityOutput = String.valueOf(timeStamp) + " login successful";
            //writing to the activity file
            FileWriter output = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(output);
            outputFile.println(activityOutput);
            outputFile.close();
            user = userName;
            loginError.setText("");
            openRecords(actionEvent);
        }else if (matches.equals("false")){
            long millis = System.currentTimeMillis();
            Timestamp timeStamp = new Timestamp(millis);
            activityOutput = String.valueOf(timeStamp) + " login not successful";
            //writing to the activity file
            FileWriter output = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(output);
            outputFile.println(activityOutput);
            outputFile.close();
            if (Locale.getDefault().getLanguage().equals("fr")) {
                String s = rb.getString("Incorrect") + rb.getString("Password");
                loginError.setText(s);
            }else {
                loginError.setText("Incorrect password");
            }

        }else{
            long millis = System.currentTimeMillis();
            Timestamp timeStamp = new Timestamp(millis);
            activityOutput = String.valueOf(timeStamp) + " login not successful";
            //writing to the activity file
            FileWriter output = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(output);
            outputFile.println(activityOutput);
            outputFile.close();
            if (Locale.getDefault().getLanguage().equals("fr")) {
                String s = rb.getString("Username") + rb.getString("does") + rb.getString("not") + rb.getString("exist");
                loginError.setText(s);
            }else {
                loginError.setText("Username does not exist");
            }
        }
    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void openRecords(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

}



