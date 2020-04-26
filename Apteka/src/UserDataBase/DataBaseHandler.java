package UserDataBase;

import java.beans.Statement;
import java.sql.*;

public class DataBaseHandler {
    private static DataBaseHandler handler=null;
    private static final String db_url="jdbc:oracle:thin:@localhost:1521:xe";
    public static Connection conn=null;
    private static Statement stmt=null;

    private DataBaseHandler() {
        //createConnection();
    }

    public static DataBaseHandler getInstance() {
        if (handler==null) {
            handler=new DataBaseHandler();
        }
        return handler;
    }

    public void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.oracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*void Apteka1() {
        String TABLE_NAME="Hippocrates";
        try {
            stmt = (Statement) conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("The table Hippocrates exists.");
            } else {
                stmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*void Apteka1() {
        String TABLE_NAME="Hippocrates";
        try {
            stmt = (Statement) conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("The table Hippocrates exists.");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (STARTS WITH 1, INCREMENTS BY 1) PRIMARY KEY,"
                        + "name varchar2(200),"
                        + "cost number(6),"
                        + "shelf life date not null);");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
