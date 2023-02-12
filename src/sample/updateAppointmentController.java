package sample;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class updateAppointmentController implements Initializable {

    public TextField appIDField;
    public TextField titleField;
    public TextField locationField;
    public TextField descriptionField;
    public Button updateButton;
    public Button exitButton;
    public TextField typeField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactField;
    public Label errorLabel;
    public TextField startTimeField;
    public TextField endTimeField;
    public DatePicker startDateField;
    public DatePicker endDateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment selected = appointmentsMainController.appointmentHandoff();
        appIDField.setText(String.valueOf(selected.getAppointmentID()));
        titleField.setText(selected.getTitle());
        locationField.setText(selected.getLocation());
        descriptionField.setText(selected.getDescription());
        typeField.setText(selected.getType());
        startDateField.setValue(selected.getStart().toLocalDateTime().toLocalDate());
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        String startTime = sdfTime.format(selected.getStart());
        startTimeField.setText(startTime);
        endDateField.setValue(selected.getStart().toLocalDateTime().toLocalDate());
        String endTime = sdfTime.format(selected.getEnd());
        endTimeField.setText(endTime);
        customerIDField.setText(String.valueOf(selected.getCustomerID()));
        userIDField.setText(String.valueOf(selected.getUserID()));
        //load all the contact options into combo box and get selected contact name base off contact id
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
        try {
            contactField.setValue(DBContacts.getContactName(selected.getContactID()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onUpdateClick(ActionEvent actionEvent) throws IOException, SQLException {
        int appID = Integer.valueOf(appIDField.getText());
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String startString = startDateField.getValue() + " " + startTimeField.getText() + ":00";
        Timestamp start = Timestamp.valueOf(startString);
        String endString = endDateField.getValue() + " " + endTimeField.getText() + ":00";
        Timestamp end = Timestamp.valueOf(endString);
        int customerID = Integer.parseInt(customerIDField.getText());
        int userID = Integer.parseInt(userIDField.getText());
        int contactID = DBContacts.getContactID((String) contactField.getValue());

        long millis=System.currentTimeMillis();
        Timestamp lastUpdated = new Timestamp(millis);
        String lastUpdatedBy = LoginController.getUser();
        if(validAppointmentTime(start, end)){
            DBAppointments.updateAppointment(appID, title, description, location, type, lastUpdated, lastUpdatedBy, start, end, customerID, userID, contactID);
            onExitClick(actionEvent);
        }else{
            errorLabel.setText("Make sure appointment times are within buisness hours(int EST)");
        }

    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsMain.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public Boolean validAppointmentTime(Timestamp start, Timestamp end){
        //make sure times are within 8am and 10pm eastern
        LocalDateTime convertedStart = start.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
        LocalDateTime convertedEnd = end.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
        int startHour = convertedStart.getHour();
        int startMinute = convertedStart.getMinute();
        int endHour = convertedEnd.getHour();
        int endMinute = convertedEnd.getMinute();
        if (startHour <= 7 || startHour >= 22 || endHour <= 7 || endHour >= 22){
            //invalid
            return false;
        }else {
            //valid
            return true;
        }
    }

}
