package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public NavbarController navbarController;
    public VBox container;
    public Pane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.isolateButtons().forEach(button -> {
            // Links redirection functionality
            button.setOnAction(event -> {
                changeContent(button.getId());
            });
        });
    }

    private void changeContent(String fxml) {
        try {
            content.getChildren().clear();
            content.getChildren().add(Utils.loadFXML(fxml));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
