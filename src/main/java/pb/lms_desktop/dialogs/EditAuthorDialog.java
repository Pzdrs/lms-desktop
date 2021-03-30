package pb.lms_desktop.dialogs;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.User;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class EditAuthorDialog extends Dialog<Author> {
    private Author author;
    private VBox container;
    private JFXTextField firstName, lastName;
    private JFXDatePicker born, died;

    public EditAuthorDialog(Author author) {
        this.author = author;
        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                return new Author(author.getId(),
                        firstName.getText().isEmpty() ? author.getFirstName() : firstName.getText(),
                        lastName.getText().isEmpty() ? author.getLastName() : lastName.getText(),
                        born.getValue() == null ? author.getBorn() : Date.from(born.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)),
                        died.getValue() == null ? author.getDied() : Date.from(died.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)),
                        author.getCreatedAt());
            }
            return null;
        });
    }

    private void init() {
        setTitle("Editing " + author.getFullName());

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        this.firstName = new JFXTextField();
        firstName.setPromptText(author.getFirstName());

        this.lastName = new JFXTextField();
        lastName.setPromptText(author.getLastName());

        this.born = new JFXDatePicker();

        this.died = new JFXDatePicker();

        this.container = new VBox(
                Utils.createInputLabel("First name:"), firstName,
                Utils.createInputLabel("Last name:"), lastName,
                Utils.createInputLabel("Born:"), born,
                Utils.createInputLabel("Died:"), died);
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.setPrefWidth(450);

        getDialogPane().setContent(container);
    }
}
