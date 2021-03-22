package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorsController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1200);

        Main.getStore().loadAuthors();
        // TODO: 3/22/2021 data covered now display them bad boys
    }
}
