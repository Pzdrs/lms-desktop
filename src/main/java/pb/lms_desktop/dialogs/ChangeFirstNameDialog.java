package pb.lms_desktop.dialogs;

import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class ChangeFirstNameDialog extends Dialog<String> {
    private TextField firstName;

    public ChangeFirstNameDialog() {
        init();
    }

    private void init() {
        setTitle("Change your first name");

        this.firstName = new TextField();



    }
}
