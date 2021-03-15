package pb.lms_desktop.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pb.lms_desktop.Main;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;

import java.net.URL;
import java.util.*;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        parameter_filter.textProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        TableView<Book> content = new TableView<>();

        TableColumn<Book, String> id = new TableColumn<>("ID");
        TableColumn<Book, String> isbn = new TableColumn<>("ISBN");
        TableColumn<Book, String> title = new TableColumn<>("Title");
        TableColumn<Book, String> pages = new TableColumn<>("Number of pages");
        TableColumn<Book, String> author = new TableColumn<>("Author");
        TableColumn<Book, String> writtenIn = new TableColumn<>("Written in");

        id.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getId()));
        isbn.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getIsbn()));
        title.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getTitle()));
        pages.setCellValueFactory(book -> new SimpleStringProperty(String.valueOf(book.getValue().getPageCount())));
        author.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getAuthor().getFirstName() + " " + book.getValue().getAuthor().getLastName()));
        writtenIn.setCellValueFactory(book -> new SimpleStringProperty(book.getValue().getWrittenIn().toString()));


        content.getColumns().addAll(Arrays.asList(id, isbn, title, pages, author, writtenIn));
        content.getItems().add(new Book("idcko", "title", "isbnko", "1950", 69,
                new Author("id", "fname", "lname", new Date(), new Date(), new Date()), new Date()));

        container.setCenter(content);
    }
}
