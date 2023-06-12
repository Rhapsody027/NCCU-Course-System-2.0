package nccu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PopoutController {

    @FXML
    private Button submit;

    @FXML
    public void submit(ActionEvent event) {
        Stage currentStage = (Stage) submit.getScene().getWindow();
        currentStage.close();
    }
}
