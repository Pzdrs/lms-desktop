package pb.lms_desktop.controllers;

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

public class CtrlNavbar implements Initializable {

    public GridPane container;
    public HBox user, links;
    public Button dashboard, books, users, profile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboard.setOnAction(event -> Main.setRoot("dashboard"));
        books.setOnAction(event -> Main.setRoot("books"));
        users.setOnAction(event -> Main.setRoot("users"));
        profile.setOnAction(event -> Main.setRoot("profile"));

        isolateButtons().forEach(button -> button.setPrefHeight(container.getPrefHeight()));

        container.prefWidthProperty().bind(Main.stage.widthProperty());

        Main.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            HBox.setMargin(user, new Insets(0, 0, 0, (Double) newValue - 700));
        });
    }

    private List<Button> isolateButtons() {
        return links.getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .collect(Collectors.toList());
    }
}
