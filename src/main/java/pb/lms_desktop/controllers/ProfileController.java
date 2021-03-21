package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.dialogs.ChangeFirstNameDialog;
import pb.lms_desktop.dialogs.ChangeLastNameDialog;
import pb.lms_desktop.store.modules.User;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public VBox container;
    public Label fullName, rank, email, registeredAt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = Main.getStore().getUser();

        // Responsive container
        container.prefWidthProperty().bind(Main.stage.widthProperty());
        container.prefHeightProperty().bind(Main.stage.heightProperty());

        // Bind information
        fullName.textProperty().bind(user.fullNameProperty());
        rank.textProperty().bind(Bindings.createStringBinding(() -> user.isIsAdmin() ? "Admin" : "User"));
        email.textProperty().bind(user.emailProperty());
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
}
