package pb.lms_desktop.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AuthorsController implements Initializable {
    public BorderPane container;
    public TableView<Author> authors;
    public List<Author> authorList;
    public TableColumn<Author, String> id, firstName, lastName, born, died;
    public JFXTextField parameter_filter;
    public JFXCheckBox parameter_aliveOnly;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1280);

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        Main.getStore().loadAuthors();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        born.setCellValueFactory(new PropertyValueFactory<>("born"));
        died.setCellValueFactory(param -> {
            if (param.getValue().getDied() == null) return new SimpleStringProperty("Hasn't died yet");
            return new SimpleStringProperty(param.getValue().getDied().toString());
        });

        resetFilters();
    }

    private void populate() {
        authors.getItems().clear();
        authorList.forEach(book -> {
            Utils.bookAvailable(book.getId());
            authors.getItems().add(book);
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
}
