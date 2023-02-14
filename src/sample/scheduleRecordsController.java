package sample;

import DBAccess.DBAppointments;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class scheduleRecordsController implements Initializable {
    public TableView table;
    public TableColumn appID;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customerID;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //order by contact id and display all necessary info for each appointment
        try {
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
}
