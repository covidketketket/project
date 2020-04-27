package user;

import UserDataBase.DataBaseHandler;
import com.sun.source.tree.WhileLoopTree;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.annotation.processing.Completion;
import javax.swing.text.TabableView;
import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userController implements Initializable {
    ObservableList<String> cityList = FXCollections.observableArrayList();
    @FXML
    public ChoiceBox<String> city;
    public static String choosedCity;
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    public void loadData() {
        cityList.removeAll();
        String[] a1 = {"*", "Нур-Султан", "Алматы", "Актобе", "Атырау", "Шымкент",
                        "Павлодар", "Караганда", "Кызылорда", "Семей", "Кокшетау"};
        cityList.addAll(a1);
        city.setValue("*");
        city.getItems().addAll(cityList);
    }
    public static String getCity() {
        return choosedCity;
    }
    public void back(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/user/user.fxml", "Apteka: log in");
    }
    public void search(MouseEvent mouseEvent) throws IOException {
        choosedCity = city.getValue();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/TableView/table.fxml", "Apteka: " + city.getValue());
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
