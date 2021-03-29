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

public class EditBookDialog extends Dialog<Book> {
    private Book book;
    private VBox container;
    private JFXTextField title, isbn, writtenIn;
    private Spinner<Integer> pageCount;
    private JFXComboBox<String> author;
    private ObservableList<String> authors;

    public EditBookDialog(Book book) {
        this.book = book;
        this.authors = FXCollections.observableArrayList();
        Main.getStore().getAuthors().forEach(author1 -> authors.add(author1.getFullName()));

        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) && validate()) {
                return new Book(book.getId(),
                        title.getText().equals("") ? book.getTitle() : title.getText(),
                        isbn.getText().equals("") ? book.getIsbn() : isbn.getText(),
                        writtenIn.getText().equals("") ? book.getWrittenIn() : writtenIn.getText(),
                        pageCount.getValue(),
                        author.getSelectionModel().isEmpty() ? book.getAuthor() : Utils.getAuthorByName(author.getValue()),
                        book.getCreatedAt());
            }
            return null;
        });
    }

    private boolean validate() {
        try {
            if (!writtenIn.getText().equals(""))
                Integer.parseInt(writtenIn.getText());
        } catch (NumberFormatException e) {
            Utils.createAlert(Alert.AlertType.ERROR, null, "Invalid year of writing", "Error").show();
            return false;
        }
        return true;
    }

    private void init() {
        setTitle("Editing book " + book.getTitle());
        setResizable(true);

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        getDialogPane().lookupButton(done).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validate()) event.consume();
        });

        this.title = new JFXTextField();
        title.setPromptText(book.getTitle());

        this.isbn = new JFXTextField();
        isbn.setPromptText(book.getIsbn());

        this.writtenIn = new JFXTextField();
        writtenIn.setPromptText(book.getWrittenIn());

        this.pageCount = new Spinner<>(0, Integer.MAX_VALUE, book.getPageCount());

        this.author = new JFXComboBox<>(authors);

        this.container = new VBox(title, isbn, writtenIn, pageCount, author);
        container.setAlignment(Pos.TOP_CENTER);

        getDialogPane().setContent(container);
    }
}
