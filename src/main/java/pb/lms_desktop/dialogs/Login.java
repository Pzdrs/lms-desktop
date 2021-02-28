package pb.lms_desktop.dialogs;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import pb.lms_desktop.References;

public class Login extends Dialog<Pair<String, String>> {
    private TextField username;
    private PasswordField password;

    public Login(Stage primaryStage) {
        init();
        setupConvertor(primaryStage);
    }

    /**
     * Initial method that sets up the dialog's looks
     */
    private void init() {
        setTitle(References.APP_NAME);
        setHeaderText("Please sign in with your credentials.");

        this.username = new TextField();
        this.password = new PasswordField();

        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        // Label's  stay
        usernameLabel.setStyle("-fx-font-weight: bold");
        passwordLabel.setStyle("-fx-font-weight: bold");

        ButtonType loginButton = new ButtonType("Log in", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Container's initial setup
        VBox container = new VBox(usernameLabel, username, passwordLabel, password);
        container.setPrefSize(500, 200);

        getDialogPane().getButtonTypes().addAll(loginButton, exitButton);

        // Make sure that both fields are not empty when clicking the Login button
        getDialogPane().lookupButton(loginButton).disableProperty()
                .bind(Bindings.createBooleanBinding(
                        () -> username.getText().trim().isEmpty() || password.getText().trim().isEmpty(),
                        username.textProperty(),
                        password.textProperty()));

        getDialogPane().setContent(container);

        // Make sure that user can start typing in their credentials right away
        username.requestFocus();
    }

    /**
     * Setup the custom result convertor
     */
    private void setupConvertor(Stage stage) {
        setResultConverter(result -> {
            if (result.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                return new Pair<>(username.getText(), password.getText());
            }
            stage.close();
            return null;
        });
    }
}
