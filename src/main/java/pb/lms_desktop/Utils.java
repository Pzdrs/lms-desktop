package pb.lms_desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

public class Utils {
    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Main.class.getResource("/scenes/" + fxml + ".fxml"));
    }

    public static boolean checkPassword(String password) {
        return BCrypt.checkpw(password, Main.getStore().getUser().getPassword());
    }
}
