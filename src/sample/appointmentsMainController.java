package sample;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

public class appointmentsMainController implements Initializable {

    public TableView<Appointment> table;
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
    public RadioButton monthRadio;
    public RadioButton weekRadio;

    public static Appointment handoff = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadAppointments(DBAppointments.appointmentsGet());
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
        if (table.getSelectionModel().getSelectedItem() != null) {
            handoff = table.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(Main.class.getResource("appointmentsUpdate.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Update appointment");
            stage.setScene(scene);
            stage.show();
        }else{
            errorLabel.setText("Please select appointment to update.");
        }
    }

    public void deleteButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            handoff = table.getSelectionModel().getSelectedItem();
            DBAppointments.appointmentDelete(handoff.getAppointmentID());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Confirm Delete of appointment ID: " + handoff.getAppointmentID() + " and appointment type: " + handoff.getType());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBAppointments.appointmentDelete(handoff.getAppointmentID());
                reloadPage(actionEvent);
            }
        }else{
            errorLabel.setText("Please select appointment to delete.");
        }
    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }
    public void reloadPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsMain.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    public void monthRadioClick(ActionEvent actionEvent) throws SQLException, IOException {
        //filter appointments by those who are in the users current month
        weekRadio.setSelected(false);
        //check for all appointments that year and month match users current
        if (monthRadio.isSelected()) {
            ObservableList<Appointment> validAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();
            for (Appointment appointment : appointments) {
                LocalDate appDate = appointment.getStart().toLocalDateTime().toLocalDate();
                LocalDate today = LocalDate.now();
                Period difference = Period.between(appDate, today);
                int years = difference.getYears();
                int months = difference.getMonths();
                if (years == 0 && months == 0) {
                    validAppointments.add(appointment);
                }
            }
            loadAppointments(validAppointments);
        }else{
            reloadPage(actionEvent);
        }

    }

    public void weekRadioClick(ActionEvent actionEvent) throws SQLException, IOException {
        //filter appointments by those who are in the users current week
        monthRadio.setSelected(false);
        //check for appointments that are within + 7 days of users current
        if (weekRadio.isSelected()) {
            ObservableList<Appointment> validAppointments = FXCollections.observableArrayList();
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();
            for (Appointment appointment : appointments) {
                LocalDate appDate = appointment.getStart().toLocalDateTime().toLocalDate();
                LocalDate today = LocalDate.now();
                Period difference = Period.between(appDate, today);
                int years = difference.getYears();
                int months = difference.getMonths();
                int days = difference.getDays();
                if (years == 0 && months == 0 && days < 7 && -7 < days) {
                    validAppointments.add(appointment);
                }
            }
            loadAppointments(validAppointments);
        }else{
            reloadPage(actionEvent);
        }

    }

    public void loadAppointments(ObservableList<Appointment> appointments){

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
    }
}
