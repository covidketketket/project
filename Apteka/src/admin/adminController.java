package admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminController implements Initializable {
    ObservableList<String> cityList = FXCollections.observableArrayList();
    ObservableList<String> sortList = FXCollections.observableArrayList();
    @FXML
    public ChoiceBox<String> city;
    @FXML
    public ChoiceBox<String> sort;
    @FXML
    public TextField drugname;

    public static String choosedCity;
    public static String chdr;
    public static String choosedSort;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    public void loadData() {
        cityList.removeAll();
        sortList.removeAll();
        String[] a1 = {"*", "Нур-Султан", "Алматы", "Актобе", "Атырау", "Шымкент",
                        "Павлодар", "Караганда", "Кызылорда", "Семей", "Кокшетау"};
        String[] a2 = {"drug_price asc", "drug_price desc", "name asc", "name desc"};
        city.setValue("*");
        sort.setValue("");
        cityList.addAll(a1);
        sortList.addAll(a2);
        city.getItems().addAll(cityList);
        sort.getItems().addAll(sortList);
    }
    public static String getCity() {
        return choosedCity;
    }
    public static String getSort() {
        return choosedSort;
    }
    public static String getDrugName() {
        return chdr;
    }
    public void back(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/login/loginSample.fxml", "Apteka: log in");
    }
    public void search(MouseEvent mouseEvent) throws IOException {
        choosedCity = "'" + city.getValue() + "'";
        chdr = drugname.getText();
        choosedSort = (sort.getValue().equals("")? "" : "order by " + sort.getValue());
        loadWindow("/ATableView/ATable.fxml", "Apteka: search result" + city.getValue());
    }
    public void upd(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/add/add.fxml", "Apteka: admin add mode" + city.getValue());
    }

    public void dlt(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/delete/delete.fxml", "Apteka: admin delete mode" + city.getValue());
    }
    public void loadWindow(String location, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void searchru(MouseEvent mouseEvent) throws IOException {
        choosedCity = "'" + city.getValue() + "'";
        chdr = drugname.getText();
        choosedSort = (sort.getValue().equals("")? "" : "order by " + sort.getValue());
        loadWindow("/RUTable/RTable.fxml", "Apteka: search rollup result" + city.getValue());
    }
}
