package pb.lms_desktop.dialogs;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChangeFirstNameDialog extends Dialog<String> {
    private TextField firstName;

    public ChangeFirstNameDialog() {
        init();
        setResultConverter(result -> firstName.getText().equals("") ? null : firstName.getText());
    }

    private void init() {
        setTitle("Change your first name");
        setHeaderText("Use the text field below to change your first name");

        this.firstName = new TextField();
        firstName.setPromptText("e.g. John Doe");

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);

        getDialogPane().getButtonTypes().addAll(done);

        getDialogPane().lookupButton(done).disableProperty().bind(Bindings.createBooleanBinding(() -> firstName.getText().equals(""), firstName.textProperty()));

        VBox container = new VBox(firstName);
        container.setPrefSize(250, 75);

        getDialogPane().setContent(container);

        firstName.requestFocus();
    }
}
