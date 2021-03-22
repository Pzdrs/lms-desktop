package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.dialogs.ChangeFirstNameDialog;
import pb.lms_desktop.dialogs.ChangeLastNameDialog;
import pb.lms_desktop.dialogs.PasswordConfirmDialog;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;
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
                () -> (newPassword.getText().isEmpty() || newPasswordConfirm.getText().isEmpty()) ||
                        !newPassword.getText().equals(newPasswordConfirm.getText()),
                newPassword.textProperty(),
                newPasswordConfirm.textProperty()));
    }

    public void changeFirstName() {
        Optional<String> firstNameDialog = new ChangeFirstNameDialog().showAndWait();
        if (!firstNameDialog.isPresent()) return;
        Pair<Integer, String> result = Main.getApi().changeUserProperty("firstName", firstNameDialog.get());
        if (result.getKey() != 200) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Couldn't change your first name, please try again later.");
            error.show();
            return;
        }
        Main.getStore().getUser().setFirstName(firstNameDialog.get());
    }

    public void changeLastName() {
        Optional<String> lastNameDialog = new ChangeLastNameDialog().showAndWait();
        if (!lastNameDialog.isPresent()) return;
        Pair<Integer, String> result = Main.getApi().changeUserProperty("lastName", lastNameDialog.get());
        if (result.getKey() != 200) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Couldn't change your last name, please try again later.");
            error.show();
            return;
        }
        Main.getStore().getUser().setLastName(lastNameDialog.get());
    }

    public void changePassword() {
        if (!BCrypt.checkpw(oldPassword.getText(), Main.getStore().getUser().getPassword())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("That's not the correct password, try again.");
            error.show();
            return;
        }
        Pair<Integer, String> result = Main.getApi().changeUserProperty("password", newPassword.getText());
        if (result.getKey() != 200) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Couldn't change your password, please try again later.");
            error.show();
            return;
        }
        try {
            HttpResponse userReloadResult = Main.getApi().get("/users/" + Main.getStore().getUser().getId());
            JSONObject user = new JSONObject(API.asText(userReloadResult.getEntity().getContent())).getJSONArray("user").getJSONObject(0);

            Main.getStore().getUser().setPassword(user.getString("password"));

            // Reset password fields
            oldPassword.setText("");
            newPassword.setText("");
            newPasswordConfirm.setText("");

            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Password changed");
            error.setHeaderText("Your password has been changed.");
            error.show();
        } catch (IOException e) {
            System.out.println("Couldn't reload user");
        }
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
