package add;

import UserDataBase.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addController implements Initializable {
    ObservableList<String> cityList = FXCollections.observableArrayList();
    DataBaseHandler handler;

    @FXML
    public ChoiceBox city;
    public TextField t11;
    public TextField t22;
    public TextField t33;
    public TextField t44;
    public TextField t55;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
        loadData();
    }
    public void save(ActionEvent actionEvent) {
        String name = t11.getText();
        String address = t22.getText();
        String schedule = t33.getText();
        String medicines = t44.getText();
        String price = t55.getText();
        if (name.isEmpty() || address.isEmpty() || schedule.isEmpty()
                || medicines.isEmpty() || price.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill all required fields!");
            alert.showAndWait();
        } else if (!isInteger(price)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Price must consist of numbers!");
            alert.showAndWait();
        } else {
            String query1 = "select max(id) as max from drugs";
            try {
                PreparedStatement noah1 = DataBaseHandler.conn.prepareStatement(query1);
                ResultSet res = noah1.executeQuery();
                res.next();
                int id = Integer.parseInt(res.getString("max"))+1;
                System.out.println(id);
                String query2 = "insert into apteki (id, city, name, address, time_schedule) " +
                        "values("+ id + ", '"+city.getValue()+"', '"+name+"', '"+address+"', '"+schedule+"')";
                PreparedStatement noah2 = DataBaseHandler.conn.prepareStatement(query2);
                noah2.execute();
                String query3 = "insert into drugs (id, name, drug) " +
                        "values("+id+", '"+name+"', '" + medicines+"')";
                PreparedStatement noah3 = DataBaseHandler.conn.prepareStatement(query3);
                noah3.execute();
                String query4 = "insert into price (drug_name, name, drug_price) " +
                        "values('"+medicines+"', '"+name+"', " + price+")";
                PreparedStatement noah4 = DataBaseHandler.conn.prepareStatement(query4);
                noah4.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(city.getValue() + " updated successfully!");
                alert.showAndWait();
            } catch (SQLException e) {
            }
        }
    }
    public void loadData() {
        cityList.removeAll();
        String[] a1 = {"Нур-Султан", "Алматы", "Актобе", "Атырау", "Шымкент",
                "Павлодар", "Караганда", "Кызылорда", "Семей", "Кокшетау"};
        city.setValue("Нур-Султан");
        cityList.addAll(a1);
        city.getItems().addAll(cityList);
    }
    public void clear(ActionEvent actionEvent) {
        t11.setText("");
        t22.setText("");
        t33.setText("");
        t44.setText("");
        t55.setText("");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/admin/admin.fxml", "Apteka: Search Helper admin mode");
    }

    public void loadWindow(String location, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void otsylka(MouseEvent mouseEvent) throws IOException {
        System.out.println("ЫЫЫЫЫ молодец!");
        loadWindow("/otsylka/OTable.fxml", "Apteka: Easter eggs from developers");
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
