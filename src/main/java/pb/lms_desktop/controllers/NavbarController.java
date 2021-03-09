package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Make navbar the same width as the window at all times
        container.prefWidthProperty().bind(Main.stage.widthProperty());

        Main.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            HBox.setMargin(user, new Insets(0, 0, 0, (Double) newValue - 600));
        });
    }

    public List<Button> isolateButtons() {
        return links.getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
    }
}
