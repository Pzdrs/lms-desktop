package pb.lms_desktop.dialogs;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONObject;
import pb.lms_desktop.Main;
import pb.lms_desktop.References;
import pb.lms_desktop.store.modules.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Login extends Dialog<Pair<String, String>> {
    private TextField username;
    private PasswordField password;
    private Label usernameLabel, passwordLabel, resultLabel;

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

        this.usernameLabel = new Label("Username");
        this.passwordLabel = new Label("Password");
        this.resultLabel = new Label();

        resultLabel.setStyle("-fx-background-radius: 5px; -fx-label-padding: 5px; -fx-background-color: #feecf0");
        usernameLabel.setStyle("-fx-font-weight: bold");
        passwordLabel.setStyle("-fx-font-weight: bold");

        ButtonType loginButton = new ButtonType("Log in", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Container's initial setup
        VBox container = new VBox(resultLabel, usernameLabel, username, passwordLabel, password);
        container.setPrefSize(500, 200);

        getDialogPane().getButtonTypes().addAll(loginButton, exitButton);

        // Make sure that both fields are not empty when clicking the Login button
        Button lookedUpButton = (Button) getDialogPane().lookupButton(loginButton);

        lookedUpButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!auth()) event.consume();
        });
        lookedUpButton.disableProperty()
                .bind(Bindings.createBooleanBinding(() -> username.getText().trim().isEmpty(), username.textProperty()));

        getDialogPane().setContent(container);

        // Make sure that user can start typing in their credentials right away
        username.requestFocus();
    }

    private boolean auth() {
        Pair<Integer, String> response = Main.getApi().login(username.getText(), formatPassword(password.getText()));
        if (response.getKey() == 200) {
            JSONObject user = new JSONObject(response.getValue()).getJSONObject("user");
            if (isAdmin(user)) {
                LocalDateTime ldt = LocalDateTime.parse(user.getString("registeredAt").split("\\.")[0]);
                Date date = new Date(ldt.toEpochSecond(ZoneOffset.ofHours(1)) * 1000);
                Main.getStore().setUser(new User(
                        new JSONObject(response.getValue()).getString("token"),
                        user.getString("_id"),
                        user.getString("username"),
                        user.getString("email"),
                        user.getString("firstName"),
                        user.getString("lastName"),
                        user.getString("password"),
                        user.getBoolean("isAdmin"),
                        date
                ));
                return true;
            }
            resultLabel.setText("You need to be an administrator to access this application");
            return false;
        }
        resultLabel.setText(new JSONObject(response.getValue()).getString("message"));
        return false;
    }

    private String formatPassword(String password) {
        if (password.equals("")) {
            return " ";
        }
        return password;
    }

    private boolean isAdmin(JSONObject user) {
        return user.getBoolean("isAdmin");
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
