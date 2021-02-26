package pb.lms_desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));

        WebView webView = new WebView();
        webView.getEngine().load("http://localhost:8080");

        primaryStage.setScene(new Scene(new VBox(webView), 1280, 720));
        primaryStage.show();
    }
}
