package login;

import UserDataBase.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private static DataBaseHandler handler;
    String defaultAdminName="admin";
    String defaultAdminPass="admin";
    String defaultUserName= "user";
    String defaultUserPass= "user";
    @FXML
    public TextField signin;
    @FXML
    private PasswordField pass;

    public void initialize(URL url, ResourceBundle rb) {
        handler= DataBaseHandler.getInstance();
    }
    public void UserSignIn(ActionEvent event) throws IOException {
        String name = signin.getText();
        String pas = pass.getText();
        if (name.isEmpty() || pas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill all required fields!");
            alert.showAndWait();
        } else if ((name.equals(defaultAdminName) && pas.equals(defaultAdminPass))) {
            ((Node) event.getSource()).getScene().getWindow().hide();
            loadWindow("/admin/admin.fxml", "Apteka: Search Helper");
        } else if (name.equals(defaultUserName) && pas.equals(defaultUserPass)){
            ((Node) event.getSource()).getScene().getWindow().hide();
            loadWindow("/user/user.fxml", "Apteka: Search Helper");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Username or password is incorrect!");
            alert.showAndWait();
        }
    }
    public void register(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/register/register.fxml", "Apteka: registration");
    }
    public void loadWindow(String location, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
