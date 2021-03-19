package pb.lms_desktop.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;
    public TableView<Book> books;
    public TableColumn<Book, String> id, isbn, title, writtenIn, author, pages;
    public TableColumn<Book, Boolean> actions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Resize and center the window cuz table too big
        Main.stage.setWidth(1462);
        Main.stage.centerOnScreen();

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        // Search bar listener
        parameter_filter.textProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        // Initialization of cell value factories for each column
        id.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getId()));
        isbn.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getIsbn()));
        title.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getTitle()));
        writtenIn.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getWrittenIn()));
        author.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getAuthor().getFirstName() + " " + book.getValue().getAuthor().getLastName()));
        pages.setCellValueFactory(book -> new SimpleStringProperty(String.valueOf(book.getValue().getPageCount())));

        // Set table data
        books.setItems(Main.getStore().getBooks());
    }
}
