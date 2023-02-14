package sample;

import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {

    public TextField nameField;
    public TextField addressField;
    public TextField numberField;
    public TextField postalField;
    public ComboBox countryField;
    public ComboBox stateField;
    public Button addButton;
    public Button exitButton;
    public Label errorLabel;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryField.getItems().addAll("U.S", "Canada", "UK");
    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void countryOnClick(ActionEvent actionEvent) throws SQLException {
        if (countryField.getValue().equals("U.S")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = DBDivisions.getDivisions(1);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
        }else if (countryField.getValue().equals("Canada")){
            stateField.getItems().clear();
            ObservableList<Division> divisions = DBDivisions.getDivisions(3);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }else{
            stateField.getItems().clear();
            ObservableList<Division> divisions = DBDivisions.getDivisions(2);
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (Division division : divisions){
                ids.add(division.getDivision());
            }
            stateField.getItems().addAll(ids);
            ids.removeAll();
        }
    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddClick(ActionEvent actionEvent) throws SQLException, IOException {
        String name = nameField.getText();
        String address = addressField.getText();
        String postalCode = postalField.getText();
        String number = numberField.getText();
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        String createdBy = "Garrett";
        Timestamp timeStamp = new Timestamp(millis);
        String division = (String) stateField.getValue();
        int divisionID = DBDivisions.getDivisionID(division);
        if (!name.equals("") && !address.equals("") && !postalCode.equals("") && !number.equals("") && division != null) {
            DBCustomers.customerAdd(name, address, postalCode, number, date, createdBy, timeStamp, createdBy, divisionID);
            onExitClick(actionEvent);
        }else{
            errorLabel.setText("Make sure all fields are filled");
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

}
