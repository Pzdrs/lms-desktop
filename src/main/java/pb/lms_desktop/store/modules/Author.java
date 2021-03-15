package pb.lms_desktop.store.modules;

import java.util.Date;

public class Author {
    private String id, firstName, lastName;
    private Date born, died, createdAt;

    public Author(String id, String firstName, String lastName, Date born, Date died, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.born = born;
        this.died = died;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
