package pb.lms_desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pb.lms_desktop.store.Store;

import java.io.IOException;

public class Main extends Application {
    private static Store store;
    public static Scene scene;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(Utils.loadFXML("index"), 1280, 720);

        primaryStage.setTitle(References.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Sign in dialog
        //Optional<Pair<String, String>> result = new Login(primaryStage).showAndWait();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(Utils.loadFXML(fxml));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Store getStore() {
        return store;
    }
}
