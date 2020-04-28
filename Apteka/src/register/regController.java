package register;

import UserDataBase.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.loginController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class regController implements Initializable {
    DataBaseHandler handler;

    @FXML
    private TextField name;
    @FXML
    private TextField passw;
    @FXML
    private TextField email;
    @FXML
    private TextField sure;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
    }

    public void UserSignIn(ActionEvent actionEvent) {
        String firstname = name.getText();
        String surename = sure.getText();
        String pas = passw.getText();
        String mail = email.getText();
        if (firstname.isEmpty() || surename.isEmpty() || pas.isEmpty() || mail.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill required fields!");
            alert.showAndWait();
        } else {
            try {
                String query = "insert into users(first_name, last_name, email, password) values("
                        + "'" + firstname + "' ,"
                        + "'" + surename + "' ,"
                        + "'" + mail + "' ,"
                        + "'" + pas + "' )";
                PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query);
                noah.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Registered successfully!");
                alert.showAndWait();
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("User with this mail already exists!");
                alert.showAndWait();
                e.printStackTrace();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Registration failed!");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }
    public void logout(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/login/loginSample.fxml", "Apteka: log in");
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
