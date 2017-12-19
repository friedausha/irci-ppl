package IRCI;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ConfirmationBoxController {
    @FXML
    public Button CancelInsertionButton = new Button();
    private Button ReenterMetadataButton = new Button();
    private Stage stage;

    @FXML
    public void initialize() {
//       CancelInsertionButton.setOnAction(e->cancelInsertion());
        ReenterMetadataButton.setOnAction(e->reenterMetadata());
    }

    @FXML
    public void cancelInsertion(ActionEvent event){
        Stage stage = (Stage) CancelInsertionButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reenterMetadata(){
        MetadataController metadataController = new MetadataController();
        metadataController.displayForm();
    }
}
