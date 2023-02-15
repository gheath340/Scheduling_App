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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public ComboBox<String> contactBox;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //order by contact id and display all necessary info for each appointment
        try {
            ObservableList<Contact> contacts = DBContacts.getContacts();
            ObservableList<String> names = FXCollections.observableArrayList();
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();

            contacts.forEach(n -> names.add(n.getName()));

            contactBox.getItems().addAll(names);
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
        Parent root = FXMLLoader.load(Main.class.getResource("customerRecords.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onContactBox(ActionEvent actionEvent) throws SQLException, IOException {
        //take name from box get id and get all appointments with that id
        int id = DBContacts.getContactID(contactBox.getValue());
        ObservableList<Appointment> appointments = DBAppointments.getContactAppointments(id);
        loadAppointments(appointments);
    }

    /**
     *
     * @param appointments
     */
    public void loadAppointments(ObservableList<Appointment> appointments){

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        table.setItems(appointments);
    }
}
