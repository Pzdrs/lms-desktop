package pb.lms_desktop.dialogs;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;

public class CreateUserDialog extends Dialog<User> {
    private VBox container;
    private JFXTextField username, firstName, lastName, email;
    private JFXPasswordField password;
    private User newUser;

    public CreateUserDialog() {
        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) && validate()) return newUser;
            return null;
        });
    }

    private boolean registerUser() {
        try {
            HttpResponse response = Main.getApi().post("/auth/signup",
                    new BasicNameValuePair("username", username.getText()),
                    new BasicNameValuePair("firstName", firstName.getText()),
                    new BasicNameValuePair("lastName", lastName.getText()),
                    new BasicNameValuePair("email", email.getText()),
                    new BasicNameValuePair("password", password.getText()));
            if (response.getStatusLine().getStatusCode() != 201) {
                JSONObject message = new JSONObject(API.asText(response.getEntity().getContent()))
                        .getJSONObject("message")
                        .getJSONArray("details").getJSONObject(0);
                Utils.createErrorAlert(message.getString("message"));
                return false;
            }
            this.newUser = Utils.parseJSONToUser(API.asText(response.getEntity().getContent()));
        } catch (IOException e) {
            Utils.createErrorAlert("Couldn't add new user, please try again later");
            return false;
        }
        return true;
    }

    private boolean validate() {
        if (username.getText().isEmpty() ||
                firstName.getText().isEmpty() ||
                lastName.getText().isEmpty() ||
                email.getText().isEmpty() ||
                password.getText().isEmpty()) {
            Utils.createErrorAlert("Some of the fields are not filled in");
            return false;
        }
        return true;
    }

    private void init() {
        setTitle("Create new user");

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        getDialogPane().lookupButton(done).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validate() || !registerUser()) event.consume();
        });

        this.username = new JFXTextField();
        username.setPromptText("e.g. johndoe69");

        this.firstName = new JFXTextField();
        firstName.setPromptText("e.g. John");

        this.lastName = new JFXTextField();
        lastName.setPromptText("e.g. Doe");

        this.email = new JFXTextField();
        email.setPromptText("e.g. johnsCool@gmail.com");

        this.password = new JFXPasswordField();
        password.setPromptText("Password");

        this.container = new VBox(
                Utils.createInputLabel("Username:"), username,
                Utils.createInputLabel("First name:"), firstName,
                Utils.createInputLabel("Last name"), lastName,
                Utils.createInputLabel("Email:"), email,
                Utils.createInputLabel("Password:"), password,
                new Label("If you wish to change this user's rank, please use the web interface."));
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.setPrefWidth(450);

        getDialogPane().setContent(container);
    }
}
