package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.dialogs.ChangeFirstNameDialog;
import pb.lms_desktop.dialogs.ChangeLastNameDialog;
import pb.lms_desktop.store.modules.User;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public VBox container, danger;
    public Label fullName, rank, registeredAt;
    public TextField email, firstName, lastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.stage.setWidth(container.getPrefWidth());
        Main.stage.centerOnScreen();

        User user = Main.getStore().getUser();

        // Responsive container
        container.prefWidthProperty().bind(Main.stage.widthProperty());
        container.prefHeightProperty().bind(Main.stage.heightProperty());

        // Bind information
        fullName.textProperty().bind(user.fullNameProperty());
        firstName.textProperty().bind(user.firstNameProperty());
        lastName.textProperty().bind(user.lastNameProperty());

        rank.textProperty().bind(Bindings.createStringBinding(() -> user.isAdmin() ? "Admin" : "User"));
        rank.getStyleClass().add(user.isAdmin() ? "adminRank" : "userRank");

        email.textProperty().bind(user.emailProperty());
        registeredAt.textProperty().bind(new SimpleStringProperty(user.getRegisteredAt().toString()));
    }

    public void changeFirstName() {
        Optional<String> result = new ChangeFirstNameDialog().showAndWait();
        if (!result.isPresent()) return;
        // TODO: 3/21/2021 change first name using api
    }

    public void changeLastName() {
        Optional<String> result = new ChangeLastNameDialog().showAndWait();
        if (!result.isPresent()) return;
        // TODO: 3/21/2021 change last name using api
    }

    public void changePassword() {
    }

    public void logout() {
    }

    public void deleteAccount() {
    }
}
