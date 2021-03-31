package pb.lms_desktop.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.http.client.methods.CloseableHttpResponse;
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

        if (Main.getStore().getUser() == null) return;

        Main.getStore().loadHistory();

        // Statistics
        try {
            CloseableHttpResponse statistics = Main.getApi().get("/statistics");
            JSONObject statisticsObject = new JSONObject(API.asText(statistics.getEntity().getContent())).getJSONObject("statistics");

            booksTotal.setText(String.valueOf(statisticsObject.getInt("booksTotal")));
            booksInUse.setText(String.valueOf(statisticsObject.getInt("booksInUse")));
            users.setText(String.valueOf(statisticsObject.getInt("users")));
            statistics.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Bind first name to the welcome message
        user.textProperty().bind(Bindings.createStringBinding(() -> ", " + Main.getStore().getUser().getFirstName() + ".", Main.getStore().getUser().firstNameProperty()));

        // Latest borrows
        int i = 0;
        for (History history : Main.getStore().getHistory()) {
            if (i == 5) break;
            Label book = new Label(history.getBook().getTitle());
            Label whenWho = new Label(Utils.timeDiff(history.getFrom()) + " ago by " + history.getUser().getFirstName());
            Label author = new Label("by " + history.getBook().getAuthor().getFullName());

            book.setStyle("-fx-font-weight: bold");
            whenWho.setStyle("-fx-text-fill: green");

            VBox vBox = new VBox(book, author, whenWho);
            vBox.setAlignment(Pos.TOP_CENTER);
            VBox.setMargin(vBox, new Insets(15, 0, 0, 0));

            latestBorrows.getChildren().add(vBox);
            i++;
        }

        i = 0;
        // My borrows
        for (History history : Main.getStore().getHistory()) {
            if (i == 5) break;
            Label book = new Label(history.getBook().getTitle());
            Label author = new Label("by " + history.getBook().getAuthor().getFullName());

            book.setStyle("-fx-font-weight: bold");

            VBox vBox = new VBox(book, author, Utils.timeRemaining(history.getTo()));
            vBox.setAlignment(Pos.TOP_CENTER);
            VBox.setMargin(vBox, new Insets(15, 0, 0, 0));

            if (history.getUser().getId().equals(Main.getStore().getUser().getId()))
                myBorrows.getChildren().add(vBox);
            i++;
        }
    }
}
