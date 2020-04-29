package delete;

import ATableView.AViewController;
import UserDataBase.DataBaseHandler;
import admin.adminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class deleteController implements Initializable {
    DataBaseHandler handler;
    public TextField del;

    public void initialize(URL url, ResourceBundle rb) {
        handler = DataBaseHandler.getInstance();
    }

    public void delete(ActionEvent actionEvent) {
        if (del.getText().isEmpty() || !isInteger(del.getText())) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setContentText("Fill all required fields!");
            alert1.showAndWait();
        } else {
            String query1 = "DELETE FROM apteki WHERE id" +
                    " =  (select id from apteki where " +
                    "id = '" + del.getText() + "')";
            String query3 = "DELETE FROM drugs WHERE id" +
                    " =  (select id from drugs where " +
                    "id = '" + del.getText() + "')";
            String query2 = "select * from apteki where " +
            "id = '" + del.getText() + "'";
            String s = "";
            try {
                PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query2);
                ResultSet res = noah.executeQuery();
                while (res.next()) {
                    s = res.getString("city") + " | " +
                            res.getString("name")+ " | " +
                            res.getString("address");
                }
            } catch (Exception e) {}
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation Dialog");
            alert2.setHeaderText("Confirm deletion process");
            alert2.setContentText("Are you want to delete " + s + " from table?");
            Optional<ButtonType> result = alert2.showAndWait();

            if (result.get() == ButtonType.OK){
                try {
                    PreparedStatement noah = DataBaseHandler.conn.prepareStatement(query1);
                    noah.executeUpdate();
                    //PreparedStatement noah1 = DataBaseHandler.conn.prepareStatement(query3);
                    //noah1.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Deleted successfully!");
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Deletion failed!");
                    alert.showAndWait();
                }
            }
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        loadWindow("/admin/admin.fxml", "Apteka: log in");
    }

    public void loadWindow(String location, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
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
