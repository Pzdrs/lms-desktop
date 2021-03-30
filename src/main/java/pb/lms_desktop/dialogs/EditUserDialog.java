package pb.lms_desktop.dialogs;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.User;

public class EditUserDialog extends Dialog<User> {
    private User user;
    private VBox container;
    private JFXTextField firstName, lastName, email;

    public EditUserDialog(User user) {
        this.user = user;
        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) && validate()) {
                return new User(user.getAccessToken(),
                        user.getId(),
                        user.getUsername(),
                        email.getText().isEmpty() ? user.getEmail() : email.getText(),
                        firstName.getText().isEmpty() ? user.getFirstName() : firstName.getText(),
                        lastName.getText().isEmpty() ? user.getLastName() : lastName.getText(),
                        user.getPassword(), user.isAdmin(), user.getRegisteredAt());
            }
            return null;
        });
    }

    private boolean validate() {
        if (!email.getText().isEmpty() && !email.getText().matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$")) {
            Utils.createErrorAlert("Invalid email address");
            return false;
        }
        return true;
    }

    private void init() {
        setTitle("Editing " + user.getFullName());

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        getDialogPane().lookupButton(done).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validate()) event.consume();
        });

        this.firstName = new JFXTextField();
        firstName.setPromptText(user.getFirstName());

        this.lastName = new JFXTextField();
        lastName.setPromptText(user.getLastName());

        this.email = new JFXTextField();
        email.setPromptText(user.getEmail());

        this.container = new VBox(
                Utils.createInputLabel("First name:"), firstName,
                Utils.createInputLabel("Last name:"), lastName,
                Utils.createInputLabel("Email:"), email);
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.setPrefWidth(450);

        getDialogPane().setContent(container);
    }
}
