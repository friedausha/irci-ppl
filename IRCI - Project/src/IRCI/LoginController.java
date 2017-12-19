package IRCI;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public Button loginButton = new Button();
    public TextField username = new TextField();
    public PasswordField password = new PasswordField();

    public void initialize(){
        loginButton.setOnAction(e->checkLogin(username, password));
    }

    @FXML
    private void checkLogin(TextField username, PasswordField password){
        if((username.getText().equals("admin")) && (password.getText().equals("admin123"))){
            displayMenu();
            System.out.println("Login berhasil");
        }
        else {
            System.out.println("Username atau password salah");
        }
    }

    @FXML
    private void displayMenu(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
