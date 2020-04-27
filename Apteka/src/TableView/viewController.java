package TableView;

import UserDataBase.DataBaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import user.userController;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class viewController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TableView<Row> aptView;
    @FXML
    private TableColumn<Row, String> ID;
    @FXML
    private TableColumn<Row, String> City;;
    @FXML
    private TableColumn<Row, String> Name;
    @FXML
    private TableColumn<Row, String> Address;
    @FXML
    private TableColumn<Row, String> Time;
    DataBaseHandler handler;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
        initiateCols();
        loadData();
    }

    public void initiateCols() {
        ID.setCellValueFactory(new PropertyValueFactory("ID"));
        City.setCellValueFactory(new PropertyValueFactory("City"));
        Name.setCellValueFactory(new PropertyValueFactory("Name"));
        Address.setCellValueFactory(new PropertyValueFactory("Address"));
        Time.setCellValueFactory(new PropertyValueFactory("Time"));
    }

    private void loadData() {
        list.removeAll(list);
        String query = "Select * from apteki where City in '" + userController.getCity() + "'";
        try {
            PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query);
            ResultSet res = noah.executeQuery();
            while (res.next()) {
                list.addAll(new Row(res.getString("ID"),
                                    res.getString("City"),
                                    res.getString("Name"),
                                    res.getString("Address"),
                                    res.getString("Time_Schedule")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        aptView.getItems().setAll(list);
    }

    public static class Row{
        private final SimpleStringProperty ID;
        private final SimpleStringProperty City;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Address;
        private final SimpleStringProperty Time;

        public Row(String id,String city,String name,String address,String time){
            this.ID = new SimpleStringProperty(id);
            this.City = new SimpleStringProperty(city);
            this.Name = new SimpleStringProperty(name);
            this.Address = new SimpleStringProperty(address);
            this.Time = new SimpleStringProperty(time);
        }
        public String getID() {
            return ID.get();
        }
        public String getCity() {
            return City.get();
        }
        public String getName() {
            return Name.get();
        }
        public String getAddress() {
            return Address.get();
        }
        public String getTime() {
            return Time.get();
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
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
