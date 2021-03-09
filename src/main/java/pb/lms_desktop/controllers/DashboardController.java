package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.setStyle("-fx-background-color: blue");
    }
}
