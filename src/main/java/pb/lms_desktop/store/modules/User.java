package pb.lms_desktop.store.modules;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pb.lms_desktop.Main;

import java.util.Date;

public class User {
    private StringProperty accessToken, id, username, email, firstName, lastName, password;
    private BooleanProperty isAdmin;
    private Date registeredAt;

    public User(String accessToken, String id, String username, String email, String firstName, String lastName, String password, boolean isAdmin, Date registeredAt) {
        this.accessToken = new SimpleStringProperty(accessToken);
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.password = new SimpleStringProperty(password);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.registeredAt = registeredAt;

        Main.getController().navbarController.username.textProperty().bind(usernameProperty());
    }

    public String getAccessToken() {
        return accessToken.get();
    }

    public StringProperty accessTokenProperty() {
        return accessToken;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public boolean isIsAdmin() {
        return isAdmin.get();
    }

    public BooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }
}
