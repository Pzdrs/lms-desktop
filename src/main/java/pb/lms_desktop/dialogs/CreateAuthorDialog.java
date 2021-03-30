package pb.lms_desktop.dialogs;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import pb.lms_desktop.API;
import pb.lms_desktop.Main;
import pb.lms_desktop.Utils;
import pb.lms_desktop.store.modules.Author;

import java.io.IOException;

public class CreateAuthorDialog extends Dialog<Author> {
    private VBox container;
    private JFXTextField firstName, lastName;
    private JFXDatePicker born, died;
    private Author newAuthor;

    public CreateAuthorDialog() {
        init();
        setupConvertor();
    }

    private void setupConvertor() {
        setResultConverter(param -> {
            if (param.getButtonData().equals(ButtonBar.ButtonData.OK_DONE) && validate()) return newAuthor;
            return null;
        });
    }

    private boolean createAuthor() {
        try {
            CloseableHttpResponse response = Main.getApi().post("/authors",
                    new BasicNameValuePair("firstName", firstName.getText()),
                    new BasicNameValuePair("lastName", lastName.getText()),
                    new BasicNameValuePair("born", born.getValue().toString()),
                    new BasicNameValuePair("died", died.getValue() == null ? null : died.getValue().toString()));
            this.newAuthor = Utils.parseJSONToAuthor(API.asText(response.getEntity().getContent()));
            response.close();
        } catch (IOException e) {
            Utils.createErrorAlert("Couldn't add new author, please try again later");
            return false;
        }
        return true;
    }

    private boolean validate() {
        if (firstName.getText().isEmpty() ||
                lastName.getText().isEmpty() || born.getValue() == null) {
            Utils.createErrorAlert("Some of the fields are not filled in");
            return false;
        }
        return true;
    }

    private void init() {
        setTitle("Create new author");

        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType close = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        getDialogPane().getButtonTypes().addAll(done, close);

        getDialogPane().lookupButton(done).addEventFilter(ActionEvent.ACTION, event -> {
            if (!validate() || !createAuthor()) event.consume();
        });

        this.firstName = new JFXTextField();
        firstName.setPromptText("e.g. Clive");

        this.lastName = new JFXTextField();
        lastName.setPromptText("e.g. Staples Lewis");

        this.born = new JFXDatePicker();

        this.died = new JFXDatePicker();

        this.container = new VBox(
                Utils.createInputLabel("First name:"), firstName,
                Utils.createInputLabel("Last name:"), lastName,
                Utils.createInputLabel("Born:"), born,
                Utils.createInputLabel("Died:"), died);
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
        container.setPrefWidth(450);

        getDialogPane().setContent(container);
    }
}
