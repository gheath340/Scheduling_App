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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class customerReportController implements Initializable {

    public TableView table;
    public TableColumn appID;
    public TableColumn title;
    public Button exitButton;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get list of all customers, display ids in first column
        //get a list of appointments for each customer then see how long list is for the count
        try {
            ObservableList<Customer> customers = DBCustomers.customersGet();
            ObservableList<Customer> finishedCustomers = FXCollections.observableArrayList();
            //for each customer get appointments, get size, set appointmentCount to size
            for (Customer customer : customers){
                ObservableList<Appointment> appointments = DBAppointments.appointmentsByCustomerID(customer.getId());
                int count = appointments.size();
                customer.setAppointmentCount(count);
                finishedCustomers.add(customer);

            }
            loadAppointments(finishedCustomers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onExitClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("appointmentsMain.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param customers
     */
    public void loadAppointments(ObservableList<Customer> customers){

        appID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        title.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));

        table.setItems(customers);
    }
}

