package pb.lms_desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import pb.lms_desktop.dialogs.LoginDialog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

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
        Main.stage.setWidth(width);
        Main.stage.centerOnScreen();
    }

    public static Date toDate(Object s) {
        if (s.toString().equals("null")) return null;
        return new Date(LocalDateTime.parse(String.valueOf(s).split("\\.")[0]).toEpochSecond(ZoneOffset.ofHours(1)) * 1000);
    }
}
