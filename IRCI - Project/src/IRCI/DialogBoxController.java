package IRCI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogBoxController{
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
        MenuController menuController = new MenuController();
        menuController.displayForm();
    }
}
