package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pb.lms_desktop.store.modules.User;


import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    public VBox container;
    public Label username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(container.getUserData() == null);
    }
}
