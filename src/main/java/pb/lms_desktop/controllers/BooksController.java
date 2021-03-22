package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    public BorderPane container;
    public TextField parameter_filter;
    public CheckBox parameter_availableOnly;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1462);

        Main.getStore().loadBooks();

        // TODO: 3/22/2021 data prepared, display those bad boys

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        // Search bar listener
        parameter_filter.textProperty().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
    }
}
