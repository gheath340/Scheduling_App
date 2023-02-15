package sample;

import DBAccess.DBAppointments;
import Model.Appointment;
import Model.AppointmentRecord;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.ResourceBundle;


public class appointmentRecordsController implements Initializable {

    public TableView table;
    public TableColumn monthColumn;
    public TableColumn typeColumn;
    public TableColumn countColumn;
    public Button exitButton;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Appointment> appointments = DBAppointments.appointmentsGet();
            ObservableList<AppointmentRecord> appointmentRecords = FXCollections.observableArrayList();

            for(Appointment appointment : appointments){
                LocalDate app = appointment.getStart().toLocalDateTime().toLocalDate();
                String month = String.valueOf(app.getMonth());
                String type = appointment.getType();
                boolean inThere = false;
                for(AppointmentRecord appRec : appointmentRecords) {
                    if (appRec.getMonth().equals(month) && appRec.getType().equals(type)) {
                        int c = Integer.parseInt(appRec.getCount());
                        c = c + 1;
                        appRec.setCount(String.valueOf(c));
                        inThere = true;
                    }
                }
                if(!inThere){
                    AppointmentRecord n = new AppointmentRecord(month, type, "1");
                    appointmentRecords.add(n);
                }
            }
            loadAppointments(appointmentRecords);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     *
     * @param appointmentRecords
     */
    public void loadAppointments(ObservableList<AppointmentRecord> appointmentRecords){

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));


        table.setItems(appointmentRecords);
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
