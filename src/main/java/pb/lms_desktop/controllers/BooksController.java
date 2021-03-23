package pb.lms_desktop.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;
    public TableView<Book> books;
    public TableColumn<Book, String> id, isbn, title, writtenIn, author;
    public TableColumn<Book, Integer> pageCount;
    public TableColumn<Book, Date> createdAt;
    private List<Book> booksList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1462);

        Main.getStore().loadBooks();
        this.booksList = Main.getStore().getBooks();

        // Setting cell value factories
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        writtenIn.setCellValueFactory(new PropertyValueFactory<>("writtenIn"));
        pageCount.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        author.setCellValueFactory(param -> param.getValue().getAuthor().getFullName());
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Populating the table
        booksList.forEach(book -> {
            books.getItems().add(book);
        });

        // TODO: 3/22/2021 data prepared, display those bad boys

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        // Search bar listener
        parameter_filter.textProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
    }
}
