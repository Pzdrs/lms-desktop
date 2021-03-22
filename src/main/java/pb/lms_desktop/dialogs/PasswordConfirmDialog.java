package pb.lms_desktop.dialogs;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Utils;

public class PasswordConfirmDialog extends Dialog<Boolean> {
    private PasswordField password;
    private Label passwordLabel, resultLabel;

    public PasswordConfirmDialog() {
        init();
        setResultConverter(result -> result.getButtonData().equals(ButtonBar.ButtonData.OK_DONE));
    }

    /**
     * Initial method that sets up the dialog's looks
     */
    private void init() {
        setTitle("Account deletion confirmation");
        setHeaderText("Please confirm this action by entering your password");

        this.password = new PasswordField();

        this.passwordLabel = new Label("Password");
        this.resultLabel = new Label();

        resultLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> !resultLabel.getText().equals(""), resultLabel.textProperty()));

        resultLabel.setStyle("-fx-background-radius: 5px; -fx-label-padding: 5px; -fx-background-color: #feecf0");
        passwordLabel.setStyle("-fx-font-weight: bold");

        ButtonType loginButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Container's initial setup
        VBox container = new VBox(resultLabel, passwordLabel, password);
        container.setPrefSize(150, 75);

        getDialogPane().getButtonTypes().addAll(loginButton, closeButton);

        // Make sure that both fields are not empty when clicking the Login button
        Button lookedUpButton = (Button) getDialogPane().lookupButton(loginButton);

        // Validation
        lookedUpButton.disableProperty().bind(Bindings.createBooleanBinding(() -> password.getText().equals(""), password.textProperty()));
        lookedUpButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!Utils.checkPassword(password.getText())) {
                event.consume();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Incorrect password");
                error.setHeaderText("That's not the correct password, please try again.");
                error.show();
            }
        });

        getDialogPane().setContent(container);
        password.requestFocus();
    }
}
