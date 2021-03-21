package pb.lms_desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import pb.lms_desktop.controllers.Controller;
import pb.lms_desktop.dialogs.LoginDialog;
import pb.lms_desktop.store.Store;

import java.util.Optional;

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
    public void stop() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Optional<Pair<Boolean, Pair<String, String>>> result = new LoginDialog().showAndWait();
        if (result.isPresent() && !result.get().getKey()) return;

        // Default tab
        controller.changeContent("dashboard");
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
