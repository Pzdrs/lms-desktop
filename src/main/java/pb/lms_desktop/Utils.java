package pb.lms_desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pb.lms_desktop.dialogs.LoginDialog;
import pb.lms_desktop.store.modules.Author;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.History;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Main.class.getResource("/scenes/" + fxml + ".fxml"));
    }

    public static boolean checkPassword(String password) {
        return BCrypt.checkpw(password, Main.getStore().getUser().getPassword());
    }

    public static Pair<Boolean, Pair<String, String>> promptLogin() {
        Optional<Pair<Boolean, Pair<String, String>>> result = new LoginDialog().showAndWait();
        if (result.isPresent() && result.get().getKey())
            return new Pair<>(true, new Pair<>(result.get().getValue().getKey(), result.get().getValue().getValue()));
        return new Pair<>(false, null);
    }

    public static void initPageSize(double width) {
        if (!Main.stage.isMaximized()) {
            Main.stage.setWidth(width);
            Main.stage.centerOnScreen();
        }
    }

    public static Date toDate(Object s) {
        if (s.toString().equals("null")) return null;
        return new Date(LocalDateTime.parse(String.valueOf(s).split("\\.")[0]).toEpochSecond(ZoneOffset.ofHours(1)) * 1000);
    }

    public static Book getBookById(String id) {
        return Main.getStore().getBooks().stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    public static User getUserById(String id) {
        return Main.getStore().getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    // Tried to use Streams but im too dumb to figure it out i guess
    public static boolean bookAvailable(String id) {
        for (History history : Main.getStore().getHistory().stream().filter(history -> history.getBook().getId().equals(id)).collect(Collectors.toList())) {
            if (!history.isReturned()) return false;
        }
        return true;
    }

    public static String timeDiff(Date date) {
        long diff = (new Date().getTime() - date.getTime()) / (1000 * 3600);
        if (diff < 24) return diff + " hours";
        return diff / 24 + " days";
    }

    public static Label timeRemaining(Date date) {
        return new Label("Due " + new SimpleDateFormat("EEEE, MMM d, yyyy HH:mm").format(date));
    }

    public static Author getAuthorByName(String fullName) {
        for (Author author : Main.getStore().getAuthors()) {
            if (author.getFullName().equals(fullName)) return author;
        }
        return null;
    }

    public static Alert createAlert(Alert.AlertType alertType, String message, String title, String header) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        if (message != null) alert.setContentText(message);
        return alert;
    }

    public static boolean createConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(message);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }
}
