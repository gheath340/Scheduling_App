package sample;

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

public class updateAppointmentController {

    public TextField appIDField;
    public TextField titleField;
    public TextField locationField;
    public TextField descriptionField;
    public Button updateButton;
    public Button exitButton;
    public TextField typeField;
    public TextField startField;
    public TextField endField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactField;

    public void onAddClick(ActionEvent actionEvent) {

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
