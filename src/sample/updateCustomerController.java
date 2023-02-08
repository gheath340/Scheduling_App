package sample;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import Model.Customer;
import Model.Division;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {

    public TextField nameField;
    public TextField addressField;
    public TextField numberField;
    public TextField postalField;
    public ComboBox countryField;
    public ComboBox stateField;
    public Button updateButton;
    public Button exitButton;
    public TextField customerIDField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryField.getItems().addAll("U.S", "Canada", "UK");
        Customer selected = customerRecordsController.handoff();

        customerIDField.setText(String.valueOf(selected.getId()));
        nameField.setText(selected.getName());
        addressField.setText(selected.getAddress());
        numberField.setText(selected.getPhone());
        postalField.setText(selected.getPostalCode());
        try {
            stateField.setValue(DBDivisions.getDivision(selected.getDivisionID()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //using customers divisionid get the country id of that division and use the country id to get country
        int countryID = 0;
        try {
            countryID = DBDivisions.getCountryID(selected.getDivisionID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String country = null;
        try {
            country = DBCountries.getCountry(countryID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        countryField.setValue(country);

        if (countryField.getValue().equals("U.S")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            try {
                divisions = DBDivisions.getDivisions(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
        }else if (countryField.getValue().equals("Canada")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            try {
                divisions = DBDivisions.getDivisions(3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }else {
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            try {
                divisions = DBDivisions.getDivisions(2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions) {
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }

    }

    public void onCountryClick(ActionEvent actionEvent) throws SQLException{
        if (countryField.getValue().equals("U.S")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            divisions = DBDivisions.getDivisions(1);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
        }else if (countryField.getValue().equals("Canada")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            divisions = DBDivisions.getDivisions(3);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }else {
            stateField.getItems().clear();
            ObservableList<Division> divisions = null;
            divisions = DBDivisions.getDivisions(2);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions) {
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }
    }

    public void onUpdateClick(ActionEvent actionEvent) throws SQLException, IOException {
        Customer selected = customerRecordsController.handoff();

        int id = selected.getId();
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = numberField.getText();
        String postal = postalField.getText();
        String country = (String) countryField.getValue();

        String division = (String) stateField.getValue();
        int divisionID = DBDivisions.getDivisionID(division);
        System.out.println(divisionID);

        long millis = System.currentTimeMillis();
        Timestamp date = new Timestamp(millis);
        String updatedBy = LoginController.getUser();

        int ra = DBCustomers.updateCustomer(id, name, address, postal, phone, date, updatedBy, divisionID);
        onExitClick(actionEvent);

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
