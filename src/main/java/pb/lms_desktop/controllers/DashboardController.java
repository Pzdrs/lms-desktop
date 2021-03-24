package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1280);

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty());
        container.prefHeightProperty().bind(Main.stage.heightProperty());

    }
}
