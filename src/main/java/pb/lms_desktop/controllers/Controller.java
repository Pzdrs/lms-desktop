package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public NavbarController navbarController;
    public VBox container;
    public Pane content;

    private String activeTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navbarController.isolateButtons().forEach(button -> {
            // Links redirection functionality
            button.setOnAction(event -> {
                changeContent(button.getId());
            });
        });
    }

    public void changeContent(String fxml) {
        try {
            activeTab = fxml;
            setupActiveLinks();

            content.getChildren().clear();
            content.getChildren().add(Utils.loadFXML(fxml));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupActiveLinks() {
        navbarController.isolateButtons().forEach(button -> {
            if (activeTab.equals(button.getId())) {
                button.getStyleClass().add("active");
            } else {
                button.getStyleClass().removeIf(s -> s.equals("active"));
            }
        });
    }
}
