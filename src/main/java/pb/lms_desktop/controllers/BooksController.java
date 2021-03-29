package pb.lms_desktop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.dialogs.CreateBookDialog;
import pb.lms_desktop.dialogs.EditBookDialog;
import pb.lms_desktop.store.modules.Book;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;
    public TableView<Book> books;
    public TableColumn<Book, String> id, isbn, title, author, createdAt;
    public TableColumn<Book, Integer> pageCount;
    public JFXButton addBook, editBook, deleteBook;
    private List<Book> booksList;
    private ObjectProperty<Book> selectedBook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1462);

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        this.selectedBook = new SimpleObjectProperty<>();

        // Load books
        Main.getStore().loadHistory();

        deleteBook.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedBook.get() == null, selectedBook));
        editBook.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedBook.get() == null, selectedBook));
        editBook.setOnAction(event -> edit(selectedBook.get()));
        deleteBook.setOnAction(event -> delete(selectedBook.get()));

        // Table setup
        books.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.selectedBook.set(newValue));

        // Setting cell value factories
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("fullTitle"));
        pageCount.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        author.setCellValueFactory(param -> param.getValue().getAuthor().fullNameProperty());
        createdAt.setCellValueFactory(param -> new SimpleStringProperty(new SimpleDateFormat("E MMMM dd yyy hh:mm a").format(param.getValue().getCreatedAt())));

        resetFilters();
    }

    private void populate() {
        books.getItems().clear();
        booksList.forEach(book -> {
            books.getItems().add(book);
        });
    }

    public void resetFilters() {
        parameter_filter.setText("");
        this.booksList = Main.getStore().getBooks();
        populate();
        deselect();
    }

    private void deselect() {
        // The first row is initially selected, which adds padding to it and looks weird so im deselecting the first row
        books.getSelectionModel().clearSelection();
    }

    public void search() {
        this.booksList = Main.getStore().getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(parameter_filter.getText().toLowerCase()))
                .collect(Collectors.toList());
        populate();
    }

    public void filterAvailable() {
        if (parameter_availableOnly.isSelected()) {
            this.booksList = this.booksList.stream()
                    .filter(book -> Utils.bookAvailable(book.getId()))
                    .collect(Collectors.toList());
        } else {
            resetFilters();
            return;
        }
        populate();
    }

    public void create() {
        Optional<Book> result = new CreateBookDialog().showAndWait();
        result.ifPresent(book -> {
            try {
                HttpResponse response = Main.getApi().post("/books",
                        new BasicNameValuePair("title", book.getTitle()),
                        new BasicNameValuePair("isbn", book.getIsbn()),
                        new BasicNameValuePair("writtenIn", book.getWrittenIn()),
                        new BasicNameValuePair("pageCount", Integer.toString(book.getPageCount())),
                        new BasicNameValuePair("author", book.getAuthor().getId()));
                Main.getStore().getBooks().add(Utils.parseJSONToBook(API.asText(response.getEntity().getContent())));
                resetFilters();
                Utils.createInfoAlert("Book successfully created", "Book created");
            } catch (IOException e) {
                Utils.createErrorAlert("Couldn't create new book, please try again later");
            }
        });
    }

    public void delete(Book book) {
        if (Utils.createConfirmationAlert("Are you sure you want to delete this book?")) {
            try {
                Main.getApi().delete("/books/" + book.getId());
                Main.getStore().getBooks().remove(book);
                resetFilters();
                Utils.createInfoAlert("Book successfully deleted", "Book deleted");
            } catch (IOException e) {
                Utils.createErrorAlert("Couldn't delete this book, please try again later");
            }
        }
    }

    public void edit(Book book) {
        EditBookDialog dialog = new EditBookDialog(book);
        Optional<Book> result = dialog.showAndWait();
        result.ifPresent(newBook -> {
            try {
                Main.getApi().patch("/books/" + book.getId(),
                        new BasicNameValuePair("title", newBook.getTitle()),
                        new BasicNameValuePair("isbn", newBook.getIsbn()),
                        new BasicNameValuePair("writtenIn", newBook.getWrittenIn()),
                        new BasicNameValuePair("pageCount", Integer.toString(newBook.getPageCount())),
                        new BasicNameValuePair("author", newBook.getAuthor().getId()));

                Main.getStore().getBooks().set(Main.getStore().getBooks().indexOf(book), newBook);
                resetFilters();

                Utils.createInfoAlert("Book successfully edited", "Book edited");
            } catch (IOException e) {
                Utils.createErrorAlert("Couldn't edit this book, please try again later");
            }
        });
    }
}
