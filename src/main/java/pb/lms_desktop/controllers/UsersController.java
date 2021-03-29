package pb.lms_desktop.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.dialogs.EditBookDialog;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UsersController implements Initializable {
    public BorderPane container;
    public Accordion users;
    public TextField parameter_filter;
    public CheckBox parameter_adminsOnly;
    private List<User> usersList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1200);

        container.prefWidthProperty().bind(Main.stage.widthProperty().subtract(16));
        container.prefHeightProperty().bind(Main.stage.heightProperty().subtract(76));

        Main.getStore().loadUsers();
        this.usersList = Main.getStore().getUsers();

        // Display admins first
        usersList.sort((user1, user2) -> Boolean.compare(user2.isAdmin(), user1.isAdmin()));

        // User details layout, idk how to do it using .fxml file and nested controllers
        showUsers();
    }

    public HBox createDetailContainer(Node... nodes) {
        HBox container = new HBox(nodes);
        container.setSpacing(5);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    public void addUser() {
        // TODO: 3/23/2021 add user button
    }

    public void resetFilters() {
        parameter_filter.setText("");
        this.usersList = Main.getStore().getUsers();
        showUsers();
    }

    public void search() {
        this.usersList = Main.getStore().getUsers().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(parameter_filter.getText().toLowerCase()))
                .collect(Collectors.toList());
        showUsers();
    }

    public void filterAdmins() {
        if (parameter_adminsOnly.isSelected()) {
            this.usersList = this.usersList.stream()
                    .filter(User::isAdmin)
                    .collect(Collectors.toList());
        } else {
            resetFilters();
            return;
        }
        showUsers();
    }

    private void showUsers() {
        // Initial clear
        this.users.getPanes().clear();

        // Populate
        usersList.forEach(user -> {
            Label fullName = new Label(user.getFullName());
            Label alias = new Label("also known as " + user.getUsername());
            Label idLabel = new Label("Identification:"), id = new Label(user.getId());
            Label emailLabel = new Label("Email:"), email = new Label(user.getEmail());
            Label registeredAtLabel = new Label("Registration:"), registeredAt = new Label(user.getRegisteredAt().toString());

            fullName.setStyle("-fx-font-weight: bold; -fx-font-size: 35px");
            fullName.getStyleClass().add(user.isAdmin() ? "userDetailAdmin" : "userDetailUser");
            alias.setStyle("-fx-font-size: 20px");
            idLabel.setStyle("-fx-font-weight: bold");
            emailLabel.setStyle("-fx-font-weight: bold");
            registeredAtLabel.setStyle("-fx-font-weight: bold");

            JFXButton delete = new JFXButton("Delete user");
            delete.setStyle("-fx-background-color: #ff5f5f");
            JFXButton edit = new JFXButton("Edit user");
            edit.setStyle("-fx-background-color: #00ccff");

            delete.setOnAction(event -> deleteUser(user));

            edit.setOnAction(event -> editUser(user));

            HBox actions = new HBox(delete, edit);
            actions.setAlignment(Pos.CENTER);
            actions.setSpacing(10);
            VBox.setMargin(actions, new Insets(25, 0, 0, 0));

            VBox detailsContainer = new VBox(
                    createDetailContainer(fullName),
                    alias,
                    new Separator(),
                    createDetailContainer(idLabel, id),
                    createDetailContainer(emailLabel, email),
                    createDetailContainer(registeredAtLabel, registeredAt),
                    actions); // TODO: 3/23/2021 Optional: add action buttons
            detailsContainer.setSpacing(10);
            detailsContainer.setAlignment(Pos.TOP_CENTER);

            TitledPane pane = new TitledPane(user.getFullName() + "  (" + user.getUsername() + ")", detailsContainer);
            pane.getStyleClass().add(user.isAdmin() ? "userDetailAdmin" : "userDetailUser");
            pane.setAlignment(Pos.TOP_CENTER);

            users.getPanes().add(pane);
        });
    }

    private void deleteUser(User user) {
    }

    private void editUser(User user) {

    }
}
