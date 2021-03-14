package pb.lms_desktop.store.modules;

import java.util.Date;

public class User {
    private String id, username, firstName, lastName, password;
    private boolean isAdmin;
    private Date registeredAt;

    public User(String id, String username, String firstName, String lastName, String password, boolean isAdmin, Date registeredAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.registeredAt = registeredAt;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }
}
