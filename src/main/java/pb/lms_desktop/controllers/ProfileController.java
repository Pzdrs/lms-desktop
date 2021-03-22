package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.dialogs.ChangeFirstNameDialog;
import pb.lms_desktop.dialogs.ChangeLastNameDialog;
import pb.lms_desktop.dialogs.PasswordConfirmDialog;
import pb.lms_desktop.store.modules.User;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public VBox container, danger;
    public Label fullName, rank, registeredAt;
    public TextField email, firstName, lastName;
    public PasswordField oldPassword, newPassword, newPasswordConfirm;
    public Button changePassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(container.getPrefWidth());

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

        // Making sure change pass button is disabled while old pass is incorrect and new passwords dont match
        changePassword.disableProperty().bind(Bindings.createBooleanBinding(
                () -> !Main.getStore().getUser().getPassword().equals(oldPassword.getText())
                        && newPassword.getText().equals(newPasswordConfirm.getText()),
                oldPassword.textProperty(),
                newPassword.textProperty(),
                newPasswordConfirm.textProperty()));
    }

    public void changeFirstName() {
        Optional<String> firstNameDialog = new ChangeFirstNameDialog().showAndWait();
        if (!firstNameDialog.isPresent()) return;
        Pair<Integer, String> result = Main.getApi().changeUserProperty("firstName", firstNameDialog.get());
        if (result.getKey() == 200)
            Main.getStore().getUser().setFirstName(firstNameDialog.get());
    }

    public void changeLastName() {
        Optional<String> lastNameDialog = new ChangeLastNameDialog().showAndWait();
        if (!lastNameDialog.isPresent()) return;
        Pair<Integer, String> result = Main.getApi().changeUserProperty("lastName", lastNameDialog.get());
        if (result.getKey() == 200)
            Main.getStore().getUser().setLastName(lastNameDialog.get());
    }

    public void changePassword() {
        // TODO: 3/21/2021 get bcrypt in here
    }

    public void logout() {
        // TODO: 3/22/2021 store refresh tokens and handle clientside
        Main.getStore().setUser(null);
        Main.getStore().getMain().startup();
    }

    public void deleteAccount() {
        new PasswordConfirmDialog().showAndWait().ifPresent(b -> {
            if (b) {
                Pair<Integer, String> result = Main.getApi().deleteUser(Main.getStore().getUser().getId());
                if (result.getKey() == 200) {
                    // Account deleted
                    Alert error = new Alert(Alert.AlertType.INFORMATION);
                    error.setTitle("Account closed");
                    error.setHeaderText("Your account has been closed, you will be logged out.");
                    error.show();
                    logout();
                } else {
                    // Couldn't delete account
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("We couldn't close your account, please try again later.");
                    error.show();
                }
            }
        });
    }
}
