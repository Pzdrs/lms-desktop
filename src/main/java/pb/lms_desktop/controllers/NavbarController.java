package pb.lms_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pb.lms_desktop.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {

    public HBox user, links;
    public Button dashboard, books, users, profile;
    public GridPane container;
    public Label loggedInStatus, username;

    private String activeTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Make navbar the same width as the window at all times
        container.prefWidthProperty().bind(Main.stage.widthProperty());
    }

    public List<Button> isolateButtons() {
        return new ArrayList<>(Arrays.asList(dashboard, books, users, profile));
    }
}
