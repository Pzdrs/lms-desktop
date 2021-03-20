package pb.lms_desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pb.lms_desktop.controllers.Controller;
import pb.lms_desktop.dialogs.Login;
import pb.lms_desktop.store.Store;

import java.io.IOException;

public class Main extends Application {
    private static Controller controller;
    private static API api;
    private static Store store;
    public static Scene scene;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/index.fxml"));

        stage = primaryStage;
        scene = new Scene(loader.load(), 1280, 720);

        controller = loader.getController();
        api = new API("http://localhost:3000");
        store = new Store();

        primaryStage.setTitle(References.APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Sign in dialog
        new Login(primaryStage).showAndWait();
    }

    public static Store getStore() {
        return store;
    }

    public static API getApi() {
        return api;
    }

    public static Controller getController() {
        return controller;
    }
}
