package pb.lms_desktop.dialogs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Book;

public class CreateBookDialog extends Dialog<Book> {
    private VBox container;
    private JFXTextField title, isbn, writtenIn;
    private Spinner<Integer> pageCount;
    private JFXComboBox<String> author;
    private ObservableList<String> authors;

    public CreateBookDialog() {
        this.authors = FXCollections.observableArrayList();
        Main.getStore().getAuthors().forEach(author1 -> authors.add(author1.getFullName()));

        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) && validate()) {
                return new Book(null, title.getText(), isbn.getText(), writtenIn.getText(), pageCount.getValue(), Utils.getAuthorByName(author.getValue()), null);
            }
            return null;
        });
    }

    private boolean validate() {
        if (title.getText().isEmpty() ||
                isbn.getText().isEmpty() ||
                writtenIn.getText().isEmpty() ||
                author.getValue() == null) {
            Utils.createErrorAlert("Some of the fields are not filled in");
            return false;
        }

        try {
            Integer.parseInt(writtenIn.getText());
        } catch (NumberFormatException e) {
            Utils.createAlert(Alert.AlertType.ERROR, null, null, "Invalid year of writing").show();
            return false;
        }
        return true;
    }

    private void init() {
        setTitle("Create new book");

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        getDialogPane().lookupButton(done).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validate()) event.consume();
        });

        this.title = new JFXTextField();
        title.setPromptText("e.g. The Hobbit");

        this.isbn = new JFXTextField();
        isbn.setPromptText("e.g. 9780007106776");

        this.writtenIn = new JFXTextField();
        writtenIn.setPromptText("e.g. 1937");

        this.pageCount = new Spinner<>(0, Integer.MAX_VALUE, 0);

        this.author = new JFXComboBox<>(authors);

        this.container = new VBox(
                Utils.createInputLabel("Title:"), title,
                Utils.createInputLabel("ISBN:"), isbn,
                Utils.createInputLabel("Year of writing:"), writtenIn,
                Utils.createInputLabel("Number of pages:"), pageCount,
                Utils.createInputLabel("Author:"), author);
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.setPrefWidth(450);

        getDialogPane().setContent(container);
    }
}
