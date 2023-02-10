package sample;

import DBAccess.DBAppointments;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class appointmentsMainController implements Initializable {

    public TableView table;
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn type;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customerID;
    public TableColumn userID;
    public TableColumn contactID;
    public Button addButton;
    public Button updateButton;
    public Button deleteButton;
    public Label errorLabel;
    public Button exitButton;

    public static Appointment handoff = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();

            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            start.setCellValueFactory(new PropertyValueFactory<>("start"));
            end.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            table.setItems(appointments);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Appointment appointmentHandoff(){ return handoff; }

    public void addButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsAdd.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void updateButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsUpdate.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Update appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteButtonClick(ActionEvent actionEvent) {

    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }
}
