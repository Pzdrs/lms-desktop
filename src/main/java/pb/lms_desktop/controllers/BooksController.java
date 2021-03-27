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
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Book;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;
    public TableView<Book> books;
    public TableColumn<Book, String> id, isbn, title, author;
    public TableColumn<Book, Integer> pageCount;
    public TableColumn<Book, Date> createdAt;
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
        Main.getStore().loadBooks();

        deleteBook.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedBook.get() == null, selectedBook));
        editBook.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedBook.get() == null, selectedBook));

        // Table setup
        books.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.selectedBook.set(newValue));

        // Setting cell value factories
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("fullTitle"));
        pageCount.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        author.setCellValueFactory(param -> param.getValue().getAuthor().getFullName());
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

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
    }

    public void delete() {
    }

    public void edit() {
    }
}
