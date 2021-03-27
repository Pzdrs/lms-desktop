package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.History;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public VBox container, latestBorrows, myBorrows;
    public Label booksTotal, booksInUse, users, user;

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

        // Bind first name to the welcome message
        user.textProperty().bind(Bindings.createStringBinding(() -> Main.getStore().getUser().getFirstName() + ".", Main.getStore().getUser().firstNameProperty()));

        int i = 0;
        for (History history : Main.getStore().getHistory()) {
            if (i == 5) break;
            if (history.getUser().getId().equals(Main.getStore().getUser().getId()))
                myBorrows.getChildren().add(new Label(history.getUser().getFullName()));

            Label whenWho = new Label(Utils.timeDiff(history.getFrom()) + " ago by " + history.getUser().getFirstName());
            Label book = new Label(history.getBook().getTitle());

            book.setStyle("-fx-font-weight: bold");
            whenWho.setStyle("-fx-text-fill: green");

            VBox latest = new VBox(book, new Label("by " + history.getBook().getAuthor().getFullName()), whenWho);
            VBox.setMargin(latest, new Insets(15, 0, 0, 0));

            latest.setAlignment(Pos.TOP_CENTER);
            latestBorrows.getChildren().add(latest);
            i++;
        }
    }
}
