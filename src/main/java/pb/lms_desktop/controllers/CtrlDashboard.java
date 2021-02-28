package pb.lms_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import pb.lms_desktop.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class CtrlDashboard implements Initializable {

    public Label booksTotal, booksActive, users;
    public AnchorPane container;
    public MenuBar bar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initStatistics();
        container.setStyle("-fx-background-color: blue");

    }

    private void initStatistics() {
        booksTotal.setText(String.valueOf(0));
        booksActive.setText(String.valueOf(0));
        users.setText(String.valueOf(0));
    }

    public void navigateBooks(ActionEvent actionEvent) {
        Main.setRoot("books");
    }

    public void handle(ActionEvent actionEvent) {
    }
}
