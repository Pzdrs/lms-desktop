package pb.lms_desktop.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public VBox container;
    public Label booksTotal, booksInUse, users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.initPageSize(1280);

        // Responsive container sizing
        container.prefWidthProperty().bind(Main.stage.widthProperty());
        container.prefHeightProperty().bind(Main.stage.heightProperty());

        Main.getStore().loadHistory();

        // Statistics
        try {
            HttpResponse statistics = Main.getApi().get("/statistics");
            JSONObject statisticsObject = new JSONObject(API.asText(statistics.getEntity().getContent())).getJSONObject("statistics");

            booksTotal.setText(String.valueOf(statisticsObject.getInt("booksTotal")));
            booksInUse.setText(String.valueOf(statisticsObject.getInt("booksInUse")));
            users.setText(String.valueOf(statisticsObject.getInt("users")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
