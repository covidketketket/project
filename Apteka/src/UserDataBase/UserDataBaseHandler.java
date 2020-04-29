package UserDataBase;

import java.sql.*;

public class UserDataBaseHandler {
    private static UserDataBaseHandler handler=null;
    private static final String db_url= "jdbc:oracle:thin:@localhost:1521/orcl";
    public static Connection conn=null;
    private static Statement stmt=null;

    private UserDataBaseHandler() {
        createConnection();
    }

    public static UserDataBaseHandler getInstance() {
        if (handler==null) {
            handler=new UserDataBaseHandler();
        }
        return handler;
    }

    public void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(db_url, "myuser", "2312");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Apteka1() {
        String TABLE_NAME="Hippocrates";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("The table Hippocrates exists.");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id number GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,"
                        + "name varchar2(200) not null,"
                        + "cost number(6) not null,"
                        + "shelf_life date not null)" +
                        "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
