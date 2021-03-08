package pb.lms_desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Utils {
    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Main.class.getResource("/scenes/" + fxml + ".fxml"));
    }


}
