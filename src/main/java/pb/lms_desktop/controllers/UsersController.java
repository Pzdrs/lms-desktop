package pb.lms_desktop.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    public BorderPane container;
    public Accordion users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1200);

        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        Main.getStore().loadUsers();

        Main.getStore().getUsers().forEach(user -> {
            Label usernameLabel = new Label("Username:"), username = new Label(user.getUsername());
            Label emailLabel = new Label("Email:"), email = new Label(user.getEmail());
            Label registeredAtLabel = new Label("Registration:"), registeredAt = new Label(user.getRegisteredAt().toString());

            usernameLabel.setStyle("-fx-font-weight: bold");
            emailLabel.setStyle("-fx-font-weight: bold");
            registeredAtLabel.setStyle("-fx-font-weight: bold");

            HBox usernameContainer = createDetailContainer(usernameLabel, username);
            HBox emailContainer = createDetailContainer(emailLabel, email);
            HBox registeredAtContainer = createDetailContainer(registeredAtLabel, registeredAt);

            VBox detailsContainer = new VBox(usernameContainer, emailContainer, registeredAtContainer);
            users.getPanes().add(new TitledPane(user.getFullName(), detailsContainer));
        });
    }

    public HBox createDetailContainer(Node... nodes) {
        HBox container = new HBox(nodes);
        container.setSpacing(5);
        container.setAlignment(Pos.CENTER);
        return container;
    }
}
