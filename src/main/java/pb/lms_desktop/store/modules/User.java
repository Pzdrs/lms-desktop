package pb.lms_desktop.store.modules;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import pb.lms_desktop.Main;

import java.util.Date;

public class User {
    private StringProperty accessToken, id, username, email, firstName, lastName, fullName, password;
    private BooleanProperty isAdmin;
    private Date registeredAt;

    public User(String accessToken, String id, String username, String email, String firstName, String lastName, String password, boolean isAdmin, Date registeredAt) {
        this.accessToken = new SimpleStringProperty(accessToken);
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.fullName = new SimpleStringProperty(firstName + " " + lastName);
        this.password = new SimpleStringProperty(password);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.registeredAt = registeredAt;

        Main.getController().navbarController.username.textProperty().bind(usernameProperty());
        Main.getApi().setHeaders(new BasicHeader(HttpHeaders.AUTHORIZATION, accessToken));
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
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

    public boolean isAdmin() {
        return isAdmin.get();
    }

    public BooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken.set(accessToken);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        this.fullName.setValue(firstName + " " + this.lastName.get());
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        this.fullName.setValue(this.firstName.get() + " " + lastName);
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "accessToken=" + accessToken +
                ", id=" + id +
                ", username=" + username +
                ", email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", password=" + password +
                ", isAdmin=" + isAdmin +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
