package sample;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {

    public TextField titleField;
    public TextField locationField;
    public TextField descriptionField;
    public Button addButton;
    public Button exitButton;
    public TextField typeField;
    public TextField startField;
    public TextField endField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactField;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get all contacts and make a list of their names
        try {
            ObservableList<Contact> contacts = DBContacts.getContacts();
            ObservableList<String> names = FXCollections.observableArrayList();
            for (Contact contact : contacts){
                names.add(contact.getName());
            }
            contactField.getItems().addAll(names);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onAddClick(ActionEvent actionEvent) throws SQLException, IOException {
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String startString = startField.getText() + ":00";
        System.out.println(startString);
        //check is start string is valid then make timestamp
        Timestamp start = null;
        Timestamp end = null;
        Boolean startValid = null;
        Boolean endValid = null;
        if(isTimeStampValid(startString)){
            //start = Timestamp.valueOf(startString);
            startValid = true;
        }else{
            errorLabel.setText("Start time is invalid");
            startValid = false;
        }
        System.out.println(startValid);
        String endString = endField.getText() + ":00";
        //check if end string is valid then make timestamp
        if(isTimeStampValid(endString)){
            //end = Timestamp.valueOf(endString);
            endValid = true;
        }else{
            errorLabel.setText("End time is invalid");
            endValid = false;
        }
        System.out.println(endValid);
        String customerIDString = customerIDField.getText();
        int customerID = Integer.valueOf(customerIDString);
        String userIDString = userIDField.getText();
        int userID = Integer.valueOf(userIDString);
        String contact = (String) contactField.getValue();
        int contactID = DBContacts.getContactID(contact);
        long millis = System.currentTimeMillis();
        Timestamp createDate = new Timestamp(millis);
        Timestamp lastUpdate = new Timestamp(millis);
        String user = LoginController.getUser();
        //check and make sure valid dates and times
        if(startValid && endValid) {
            DBAppointments.appointmentAdd(title, description, location, type, createDate, user, lastUpdate,
                    user, start, end, customerID, userID, contactID);
            onExitClick(actionEvent);
        }
        //check to make sure customer and userIDs are valid

    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsMain.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public static Boolean isTimeStampValid(String inputString)
    {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            format.parse(inputString);
            return true;
        }
        catch(ParseException e)
        {
            return false;
        }
    }

}
