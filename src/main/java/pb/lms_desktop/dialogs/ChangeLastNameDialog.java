package pb.lms_desktop.dialogs;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChangeLastNameDialog extends Dialog<String> {
    private TextField lastName;

    public ChangeLastNameDialog() {
        init();
        setResultConverter(result -> !lastName.getText().equals("") && result.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) ? lastName.getText() : null);
    }

    private void init() {
        setTitle("Change your last name");
        setHeaderText("Use the text field below to change your last name");

        this.lastName = new TextField();
        lastName.setPromptText("e.g. Doe");

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);

        getDialogPane().getButtonTypes().addAll(done);

        getDialogPane().lookupButton(done).disableProperty().bind(Bindings.createBooleanBinding(() -> lastName.getText().equals(""), lastName.textProperty()));

        VBox container = new VBox(lastName);
        container.setPrefSize(250, 75);

        getDialogPane().setContent(container);

        lastName.requestFocus();
    }
}
