package sample;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerRecordsController implements Initializable {

    public TableView<Customer> table;
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerPostalCode;
    public TableColumn customerPhone;
    public TableColumn customerDivision;
    public Button addButton;
    public Button updateButton;
    public Button deleteButton;
    public Label errorLabel;

    public static Customer handoff = null;
    public Button appointmentsButton;
    public Label appointmentLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Customer> customers = DBCustomers.customersGet();

            customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            table.setItems(customers);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Long currentTime = System.currentTimeMillis();
        Boolean appSoon = false;
        try {
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();
            //ObservableList<Long> times = FXCollections.observableArrayList();
            //appointments.forEach(n -> {
                //times.add(n.getStart().getTime() - currentTime);
            //});
            //times.forEach(n -> );

            for (Appointment appointment : appointments){
                Long difference = (appointment.getStart().getTime() - currentTime);
                if (difference <= 900000 && difference > 0){
                    appSoon = true;
                    appointmentLabel.setText("Appointment ID: " + appointment.getAppointmentID() + " at " + appointment.getStart() + " is coming up.");
                }
            }
            if (!appSoon){
                appointmentLabel.setText("No appointments in the next 15 minutes.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Customer handoff(){ return handoff; }

    public void addButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("addCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void updateButtonClick(ActionEvent actionEvent) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            handoff = table.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(Main.class.getResource("updateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        }else{
            errorLabel.setText("Please select customer to update.");
        }

    }

    public void deleteButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        //check all appointments to make sure none of their customer ids match selected customers id
        errorLabel.setText("");
        Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null && customerDeleteCheck(selectedCustomer.getId())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete of " + selectedCustomer.getName());
            alert.setContentText("Confirm Delete of " + selectedCustomer.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DBCustomers.customerDelete(selectedCustomer.getId());
                reloadPage(actionEvent);
            }
        }else if(selectedCustomer == null){
            errorLabel.setText("Please select a customer");
        }else if (!customerDeleteCheck(selectedCustomer.getId())){
            errorLabel.setText("Please delete all of customers appointments before deleting customer");
        }
    }

    public void reloadPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

    public void onAppointmentsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("appointmentsMain.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    //returns true if okay to be deleted(no appointments associated)
    public boolean customerDeleteCheck(int id) throws SQLException {
        //check given customer id against all customer ids in all appointments
        Boolean checker = true;
        ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();

        for (Appointment appointment : appointments){
            int tempID = appointment.getCustomerID();
            if (tempID == id) {
                checker = false;
            }
        }
        return checker;
    }

}
