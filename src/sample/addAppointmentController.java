package sample;

import DBAccess.DBAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class addAppointmentController {

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

    public void onAddClick(ActionEvent actionEvent) {
        String title = titleField.getText();
        String location = locationField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String startString = startField.getText();
        Timestamp start = Timestamp.valueOf(startString);
        String endString = endField.getText();
        Timestamp end = Timestamp.valueOf(endString);
        String customerID = customerIDField.getText();
        String userID = userIDField.getText();
        String contact = (String) contactField.getValue();
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        String user = LoginController.getUser();

        DBAppointments.appointmentAdd(title, description, location, type, date, user, date,
                user, start, end, customerID, userID, contactID);
    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsMain.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
