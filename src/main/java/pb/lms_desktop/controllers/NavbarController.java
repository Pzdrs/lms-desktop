package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pb.lms_desktop.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NavbarController implements Initializable {

    public HBox user, links;
    public Button dashboard, books, users, profile;
    public GridPane container;
    public Label loggedInStatus, username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Make navbar the same width as the window at all times
        container.prefWidthProperty().bind(Main.stage.widthProperty());
    }

    public List<Button> isolateButtons() {
        return links.getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
    }
}
