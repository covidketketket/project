package ATableView;

import UserDataBase.DataBaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import admin.adminController;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AViewController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TableView<Row> aptView;
    @FXML
    private TableColumn<Row, String> Medicine;
    @FXML
    private TableColumn<Row, String> Pharmacy;;
    @FXML
    private TableColumn<Row, String> City;
    @FXML
    private TableColumn<Row, String> Address;
    @FXML
    private TableColumn<Row, String> Time;
    @FXML
    private TableColumn<Row, String> Price;
    DataBaseHandler handler;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
        initiateCols();
        loadData();
    }

    public void initiateCols() {
        Medicine.setCellValueFactory(new PropertyValueFactory("Medicine"));
        Pharmacy.setCellValueFactory(new PropertyValueFactory("Pharmacy"));
        City.setCellValueFactory(new PropertyValueFactory("City"));
        Address.setCellValueFactory(new PropertyValueFactory("Address"));
        Time.setCellValueFactory(new PropertyValueFactory("Time"));
        Price.setCellValueFactory(new PropertyValueFactory("Price"));
    }

    private void loadData() {
        list.removeAll(list);
        String c = adminController.getCity();
        String query = "select INITCAP(drug_name) as drug_name, INITCAP(name) as name, city, address, time_schedule,"
                + "drug_price from apteki natural join drugs natural join price "
                + "where city = " + ((c.equals("'*'")) ? "city" : c) + " and drug_name like '%"
                + adminController.getDrugName().toLowerCase() + "%' " + adminController.getSort();
        try {
            PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query);
            ResultSet res = noah.executeQuery();
            while (res.next()) {
                list.addAll(new Row(res.getString("DRUG_NAME"),
                                    res.getString("NAME"),
                                    res.getString("CITY"),
                                    res.getString("ADDRESS"),
                                    res.getString("TIME_SCHEDULE"),
                                    res.getString("DRUG_PRICE")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        aptView.getItems().setAll(list);
    }

    public static class Row{
        private final SimpleStringProperty Medicine;
        private final SimpleStringProperty Pharmacy;
        private final SimpleStringProperty City;
        private final SimpleStringProperty Address;
        private final SimpleStringProperty Time;
        private final SimpleStringProperty Price;

        public Row(String medicine, String pharmacy, String city,
                   String address,String time, String price){
            this.Medicine = new SimpleStringProperty(medicine);
            this.Pharmacy = new SimpleStringProperty(pharmacy);
            this.City = new SimpleStringProperty(city);
            this.Address = new SimpleStringProperty(address);
            this.Time = new SimpleStringProperty(time);
            this.Price = new SimpleStringProperty(price);
        }
        public String getMedicine() {
            return Medicine.get();
        }
        public String getPharmacy() {
            return Pharmacy.get();
        }
        public String getCity() {
            return City.get();
        }
        public String getAddress() {
            return Address.get();
        }
        public String getTime() {
            return Time.get();
        }
        public String getPrice() {
            return Price.get();
        }
    }

}
