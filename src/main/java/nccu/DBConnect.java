package nccu;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnect {

    private static Connection conn;
    private static String dbName = "test";
    private static String user = "vscode";
    private static String password = "2003";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, user, password);
            System.out.println("Connect!");

            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static ObservableList<test> getData() throws SQLException {
        Connection conn = connect();
        ObservableList<test> list = FXCollections.observableArrayList();

        try {
            PreparedStatement stat = conn.prepareStatement("SELECT * from customer");
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                list.add(new test(rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}