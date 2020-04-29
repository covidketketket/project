package otsylka;

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

public class OViewController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TableView<Row> aptView;
    @FXML
    private TableColumn<Row, String> Name;
    @FXML
    private TableColumn<Row, String> Surename;;
    @FXML
    private TableColumn<Row, String> ID;
    DataBaseHandler handler;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
        initiateCols();
        loadData();
    }

    public void initiateCols() {
        Name.setCellValueFactory(new PropertyValueFactory("Name"));
        Surename.setCellValueFactory(new PropertyValueFactory("Surename"));
        ID.setCellValueFactory(new PropertyValueFactory("ID"));
    }

    private void loadData() {
        list.removeAll(list);
        String query = "select INITCAP(name) as name," +
        "case " +
        "when surename = 'Baimolda' then 'Baimolda: coder'" +
        "when surename = 'Seitzhan' then 'Seitzhan: designer' " +
        "when surename = 'Kazhymukhanov' then 'Kazhymukhanov: database dev' " +
        "else 'not dev' " +
        "end as surename, " +
        "to_char(id) as id " +
        "from otsylka";
        try {
            PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query);
            ResultSet res = noah.executeQuery();
            while (res.next()) {
                list.addAll(new Row(res.getString("Name"),
                                    res.getString("Surename"),
                                    res.getString("ID")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        aptView.getItems().setAll(list);
    }

    public static class Row{
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Surename;
        private final SimpleStringProperty ID;

        public Row(String Name, String Surename, String ID){
            this.Name = new SimpleStringProperty(Name);
            this.Surename = new SimpleStringProperty(Surename);
            this.ID = new SimpleStringProperty(ID);
        }
        public String getName() {
            return Name.get();
        }
        public String getSurename() {
            return Surename.get();
        }
        public String getID() {
            return ID.get();
        }
    }

}
