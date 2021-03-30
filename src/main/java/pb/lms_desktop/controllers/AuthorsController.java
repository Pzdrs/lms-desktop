package pb.lms_desktop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.dialogs.*;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AuthorsController implements Initializable {
    public BorderPane container;
    public TableView<Author> authors;
    public TableColumn<Author, String> id, firstName, lastName, born, died;
    public JFXTextField parameter_filter;
    public JFXCheckBox parameter_aliveOnly;
    public JFXButton deleteAuthor, editAuthor;
    private List<Author> authorList;
    private ObjectProperty<Author> selectedAuthor;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1280);

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        this.selectedAuthor = new SimpleObjectProperty<>();

        // Load authors
        Main.getStore().loadAuthors();

        deleteAuthor.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedAuthor.get() == null, selectedAuthor));
        editAuthor.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedAuthor.get() == null, selectedAuthor));
        deleteAuthor.setOnAction(event -> delete(selectedAuthor.get()));
        editAuthor.setOnAction(event -> edit(selectedAuthor.get()));

        // Table setup
        authors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.selectedAuthor.set(newValue));

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        born.setCellValueFactory(param -> new SimpleStringProperty(new SimpleDateFormat("dd MMM yyy").format(param.getValue().getBorn())));
        died.setCellValueFactory(param -> {
            // Make sure there is no empty cell when the author is still alive to this day
            if (param.getValue().getDied() == null) return new SimpleStringProperty("Hasn't died yet");
            return new SimpleStringProperty(new SimpleDateFormat("dd MMM yyy").format(param.getValue().getDied()));
        });

        resetFilters();
    }

    private void populate() {
        authors.getItems().clear();
        authorList.forEach(author -> {
            authors.getItems().add(author);
        });
    }

    public void resetFilters() {
        parameter_filter.setText("");
        this.authorList = Main.getStore().getAuthors();
        populate();
        deselect();
    }

    private void deselect() {
        // The first row is initially selected, which adds padding to it and looks weird so im deselecting the first row
        authors.getSelectionModel().clearSelection();
    }

    public void search() {
        this.authorList = Main.getStore().getAuthors().stream()
                .filter(author -> author.getFirstName().toLowerCase().contains(parameter_filter.getText().toLowerCase()) ||
                        author.getLastName().toLowerCase().contains(parameter_filter.getText().toLowerCase()))
                .collect(Collectors.toList());
        populate();
    }

    public void filterAlive() {
        if (parameter_aliveOnly.isSelected()) {
            this.authorList = this.authorList.stream()
                    .filter(author -> author.getDied() == null)
                    .collect(Collectors.toList());
        } else {
            resetFilters();
            return;
        }
        populate();
    }

    public void delete(Author author) {
        if (Utils.createConfirmationAlert("Are you sure you want to delete this author?")) {
            try {
                Main.getApi().delete("/authors/" + author.getId()).close();
                Main.getStore().getAuthors().remove(author);
                resetFilters();
                Utils.createInfoAlert("Author successfully deleted", "Author deleted");
            } catch (IOException e) {
                Utils.createErrorAlert("Couldn't delete this author, please try again later");
            }
        }
    }

    public void edit(Author author) {
        Optional<Author> result = new EditAuthorDialog(author).showAndWait();
        result.ifPresent(author1 -> {
            try {
                CloseableHttpResponse response = Main.getApi().patch("/authors/" + author.getId(),
                        new BasicNameValuePair("firstName", author1.getFirstName()),
                        new BasicNameValuePair("lastName", author1.getLastName()),
                        new BasicNameValuePair("born", String.valueOf(author1.getBorn().getTime())),
                        new BasicNameValuePair("died", String.valueOf(author1.getDied().getTime())));
                if (response.getStatusLine().getStatusCode() == 200) {
                    Main.getStore().getAuthors().set(Main.getStore().getAuthors().indexOf(author), author1);
                    resetFilters();
                    Utils.createInfoAlert("Author successfully edited", "Author edited");
                }
                response.close();
            } catch (IOException e) {
                Utils.createErrorAlert("Couldn't edit this author, please try again later");
            }
        });
    }

    public void create() {
        Optional<Author> result = new CreateAuthorDialog().showAndWait();
        result.ifPresent(author -> {
            Main.getStore().getAuthors().add(author);
            resetFilters();
            Utils.createInfoAlert("New author created successfully", "Author created");
        });
    }
}
