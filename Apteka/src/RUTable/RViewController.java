package RUTable;

import UserDataBase.DataBaseHandler;
import admin.adminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RViewController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TableView<Row> aptView;
    @FXML
    private TableColumn<Row, String> City;
    @FXML
    private TableColumn<Row, String> Price;
    DataBaseHandler handler;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
        initiateCols();
        loadData();
    }

    public void initiateCols() {
        City.setCellValueFactory(new PropertyValueFactory("City"));
        Price.setCellValueFactory(new PropertyValueFactory("Price"));
    }

    private void loadData() {
        list.removeAll(list);
        String c = adminController.getCity();
        String query = "select city, Round(AVG(drug_price),2) as drug_price from apteki natural " +
                "join drugs natural join price  where city = " + ((c.equals("'*'")) ? "city" : c) +
                " and drug_name like '%" + adminController.getDrugName() + "' " + " group by rollup(city)";
        try {
            PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query);
            ResultSet res = noah.executeQuery();
            while (res.next()) {
                list.addAll(new Row(res.getString("CITY"),
                                    res.getString("DRUG_PRICE")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        aptView.getItems().setAll(list);
    }

    public static class Row{
        private final SimpleStringProperty City;
        private final SimpleStringProperty Price;

        public Row(String city, String price){
            this.City = new SimpleStringProperty(city);
            this.Price = new SimpleStringProperty(price);
        }
        public String getCity() {
            return City.get();
        }
        public String getPrice() {
            return Price.get();
        }
    }

}
