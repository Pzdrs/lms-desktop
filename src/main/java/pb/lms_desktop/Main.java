package pb.lms_desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static Scene scene;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(Utils.loadFXML("dashboard"), 1280, 720);

        primaryStage.setTitle(References.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Sign in dialog
        //Optional<Pair<String, String>> result = new Login(primaryStage).showAndWait();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(Utils.loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
