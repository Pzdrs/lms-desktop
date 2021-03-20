package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Label label = new Label(Main.getStore().getUser().toString());
        label.setWrapText(true);
        container.getChildren().add(label);
    }
}
