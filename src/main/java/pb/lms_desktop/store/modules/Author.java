package pb.lms_desktop.store.modules;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class Author {
    private StringProperty id, firstName, lastName, fullName;
    private Date born, died, createdAt;

    public Author(String id, String firstName, String lastName, Date born, Date died, Date createdAt) {
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.fullName = new SimpleStringProperty(firstName + " " + lastName);
        this.born = born;
        this.died = died;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
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

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public Date getBorn() {
        return born;
    }

    public Date getDied() {
        return died;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", born=" + born +
                ", died=" + died +
                ", createdAt=" + createdAt +
                '}';
    }
}
