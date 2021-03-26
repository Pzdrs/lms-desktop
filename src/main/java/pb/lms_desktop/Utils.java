package pb.lms_desktop;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pb.lms_desktop.dialogs.LoginDialog;
import pb.lms_desktop.store.modules.Book;
import pb.lms_desktop.store.modules.History;
import pb.lms_desktop.store.modules.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Main.class.getResource("/scenes/" + fxml + ".fxml"));
    }

    public static Parent loadPartialFXML(String fxml) throws IOException {
        return FXMLLoader.load(Main.class.getResource("/partials/" + fxml + ".fxml"));
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
        Main.stage.setWidth(width);
        Main.stage.centerOnScreen();
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

    public static boolean bookAvailable(String id) {
        List<History> histories = Main.getStore().getHistory().stream()
                .filter(history -> history.getBook().getId().equals(id))
                .filter(history -> !history.isReturned())
                .collect(Collectors.toList());
        System.out.println(histories.size());
        return histories.size() != 0;
    }
}
